/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qsolutions.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.text.html.HTMLEditorKit;
import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import qsolutions.api.*;
import qsolutions.exam.MultipleChoice;


/**
 * ActionListener to import the customer format
 */
@ActionID(category = "MyFile",
id = "qsolutions.action.ImportCustomerFormatAction")
@ActionRegistration(displayName = "qsolutions.action.Bundle#CUSTOMERFORMAT")
@ActionReferences(
{
    @ActionReference(path = "Menu/MyFile/Import", position = 0)
})
@Messages("CTL_ImportCustomerFormatAction=qsolutions.action.Bundle#CUSTOMERFORMAT")
public final class ImportCustomerFormatAction implements ActionListener
{
    /** Maximum number of choices that can be accepted by a multiple choice 
     * question
     */
    private final static int kSupportedNumChoices = 6;
    /** Minimum line length in the special format */
    private final static int kSpecialMinLineLength = 4;
    /** Number of characters from beginning of bracket*/
    private final static int kBeginningBracket = 3;
    
    /** Parse exception*/
    private final static int kParseErr = 0;
    /** Parse exception*/
    private final static int kBoundsErr = 1;
    /** Parse exception*/
    private final static int kFileErr = 2;
    /** Parse exception*/
    private final static int kOtherErr = 3;
    
    private static final String kExamEditPrompt =
            " THE CURRENT EXAM HAS BEEN EDITED BUT NOT SAVED, WOULD YOU TO" +
            " LIKE TO IMPORT A NEW EXAM ANYWAYS?";
    private static final String kBundleName = "qsolutions/action/Bundle";
    private static final ResourceBundle kBundle =
            ResourceBundle.getBundle(kBundleName);
    
    /**
     * Confirm dialog
     * @return confirm return
     */
    private int importConfirm()
    {
        return javax.swing.JOptionPane.showConfirmDialog(null,
                    kBundle.getString(kExamEditPrompt),
                    kBundle.getString("CONFIRM CLOSE"),
                    javax.swing.JOptionPane.YES_NO_OPTION);  
    }
    
    private void importQuarantine(ExamApi exam, File file) throws Exception
    {
        exam.newExam();
        exam.getTitleGroup().setShowTitleGroup(false);
        exam.getTitleGroup().setTitle(file.getName());
        importSpecialFormat(file);
    }
    
    /**
     * Action handler for importing customer format
     * @param e unused
     */
    public void actionPerformed(ActionEvent e)
    {
        int confirm = javax.swing.JOptionPane.YES_OPTION;
        
        // If the exam has been edited
        if (Lookup.getDefault().lookup(ExamHandlerApi.class).getEdited())
        {
            //TODO change to a normal save, discard, cancel
            confirm = importConfirm();           
        }
        
        // If the user does want to make a new exam
        if ( confirm == javax.swing.JOptionPane.YES_OPTION )
        {
            final javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
            int returnVal;
            File file;
            ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
            returnVal = fc.showOpenDialog(null);
            // If the user chose a file
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION)
            {
                file = fc.getSelectedFile();

                //if the file does not exists
                if (!file.isFile())
                {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            kBundle.getString("FILE DOES NOT EXIST"));
                }
                else
                {
                    try
                    {
                        importQuarantine(exam, file);
                    }
                    catch (java.text.ParseException ex)
                    {
                        printExceptionParse(ex);
                    }
                    catch (ArrayIndexOutOfBoundsException ex)
                    {
                        printExceptionBounds(ex);
                    }
                    catch (java.io.FileNotFoundException ex)
                    {
                        printExceptionFile(ex);
                    }
                    catch ( Exception ex )
                    {
                        printException(ex);
                    }
                }
            }
        }
        
    }
    
    private void printExceptionParse(Exception ex)
    {
        javax.swing.JOptionPane.showMessageDialog(null,
            "Error while parsing on line: " 
                + ((java.text.ParseException)ex).getErrorOffset());
        
    }
    
    private void printExceptionBounds(Exception ex)
    {
        javax.swing.JOptionPane.showMessageDialog(null,
            "Multiple choice question limit exceeded: can only "
                + "accept up to 6 choice");
    }
    
    private void printExceptionFile(Exception ex)
    {
        javax.swing.JOptionPane.showMessageDialog(null,
            "Imported file not found"); 
        
    }
    
    
    private void printException(Exception ex)
    {
        javax.swing.JOptionPane.showMessageDialog(null,
            "Input format is invalid");    
    }
    
    private static String addHTMLTags(String prompt)
    {
        javax.swing.JTextPane textPane = new javax.swing.JTextPane();
        textPane.setEditorKit(new HTMLEditorKit());
        textPane.setText(prompt);
        return textPane.getText();
    }
    
    
    /**
     * Imports a special file type specified by the customer.
     * @param file a file formatted in an
     * @return an exam constructed from the imported file
     * Preconditions: file != null && file.exists()
     */
    private static void importSpecialFormat(File file) 
        throws java.io.FileNotFoundException, java.text.ParseException
    {
        FileReader rdr = new FileReader(file);
        Scanner in = new Scanner(rdr);
        ExamApi exam = Lookup.getDefault().lookup(ExamApi.class);
        
        DocumentItemApi examItem = null;
        int lineNum = 0;
        // Keep reading lines until end of file
        while (in.hasNextLine())
        {
            String line = in.nextLine();
            lineNum++;
            String tag = "";
            String prompt = line;
            int closebracket = line.indexOf("]");
            
            // Make sure line meets minimum specifications
            if (line.length() >= kSpecialMinLineLength && line.startsWith("["))
            {
                tag = line.substring(0, closebracket+1);
                prompt = "";
                
                // Get the prompt from the line
                if (line.length() > kSpecialMinLineLength)
                {
                    prompt = line.substring(closebracket+1).trim();
                }
            }
            prompt = prompt.replaceAll("(<BR>)|(<br>)", "<p></p>"); 
            
            // Add HTML tags from the text pane
            prompt = addHTMLTags(prompt);
            
            // If it's an instruction line
            if (tag.equals("[  ]"))
            {
                examItem = handleInst(examItem, prompt);
            }
            
            // If it's a true false line
            if (tag.equals("[TF]"))
            {
                examItem = handleTF(examItem, prompt);
            }
            
            // If it's a short answer line
            if (tag.startsWith("[SA"))
            {
                examItem = handleFR(line, examItem, prompt, closebracket);
            }
            
            // If it's a multiple choice line
            if (tag.startsWith("[MC"))
            {
                examItem = handleMC(line, examItem, prompt, in);
                
            }
            
            // If it's anything else, throw an error
            if (examItem == null)
            {
                throw new java.text.ParseException("Format error found", 
                        lineNum);
            }
            exam.addItem(examItem);
            examItem = null;
        }
    }
    
    private static DocumentItemApi handleMC(String line, 
            DocumentItemApi examItem, String prompt, Scanner in)
    {
        int desiredLines = Integer.parseInt(line.substring(
                    kBeginningBracket, kBeginningBracket+1));
        // the customer has more lines than we support
        if (desiredLines > kSupportedNumChoices)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        List<String> ansList = new ArrayList<String>();
        // Loop through until desired lines decrements to 0
        while (desiredLines > 0)
        {
            ansList.add(in.nextLine());
            desiredLines--;
        }
        examItem = Lookup.getDefault().lookup(
                MultipleChoiceApi.class).newItem();
        examItem.setText(prompt);
        ((MultipleChoiceApi)examItem).clearChoices();
        // Add choices from the answer list
        for( int choice = 0; choice < ansList.size(); choice++)
        {
            ((MultipleChoiceApi)examItem).addChoice( 
                ansList.get(choice), true, choice == 0 );
        }
        return examItem;
    }
    
    private static DocumentItemApi handleFR(String line, 
            DocumentItemApi examItem, String prompt, int closebracket)
    {
        // Number of lines must be in range 0-99
        int desiredLines = Integer.parseInt(line.substring(
            kBeginningBracket, closebracket));
        examItem = Lookup.getDefault().lookup(
                FreeResponseApi.class).newItem();
        examItem.setText(prompt);
        ((FreeResponseApi)examItem).setExtraLines(desiredLines);
        return examItem;
    }
    
    private static DocumentItemApi handleTF(DocumentItemApi examItem, 
            String prompt)
    {
        examItem = Lookup.getDefault().lookup(
                        TrueFalseApi.class).newItem();
        examItem.setText(prompt);
        return examItem;
    }
    
    private static DocumentItemApi handleInst(DocumentItemApi examItem, 
            String prompt)
    {
        examItem = Lookup.getDefault().lookup(
                        InstructionsApi.class).newItem();
        examItem.setText(prompt);
        return examItem;
    }
}
