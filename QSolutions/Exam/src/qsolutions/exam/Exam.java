package qsolutions.exam;

import java.io.Serializable;
import java.util.*;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import qsolutions.api.*;

/**
 * This class stores a list of questions and contains several
 * methods used to edit the list. Questions must be added individually and a new
 * Exam is always empty.
 */
@ServiceProvider(service = ExamApi.class)
public class Exam extends Observable implements Serializable, ExamApi, Observer
{
    private ArrayList<DocumentItemApi> items; //The list of items in a test
    // Clipboard used for copy/cut/paste operations
    private ArrayList<DocumentItemApi> clipboard;
    private FormatApi format; //The database of formatting options
    private TitleGroupApi titlegroup; //The database of formatting options
    private HeaderFooterApi headerfooter; //The database of formatting options
    
    /** The HTML tags that are placed at the beginning of the exam question list
     (after headers) */
    public static final String kExamItemListHTML = 
            "<ol style=\"list-style: decimal;\">";
    /** The HTML tags that are put at the end of the exam */
    public static final String kEndExamItemListHTML = "</ol>";
    /** The integer representing six */
    public static final int kSix = 6;
    /** Boolean telling bulk update to start */
    public static final boolean kStart = true;
    /** Boolean telling bulk update to end */
    public static final boolean kEnd = false;
    private static final int kMCIndex = 3;
    private static final int kORDIndex = 4;
    private static final int kTFIndex = 5;
    
    private boolean isAnswerDoc;
    private Random random = new Random(); //A random number generation tool
    private String selected;
    private String path = null;
    private boolean bulkUpdating = kEnd;
    
    private static final String kBundleName = "qsolutions/exam/Bundle";
    private static final ResourceBundle kBundle = ResourceBundle.getBundle(
            kBundleName);
    

    /**
     * Initializes both the item list and the format object
     */
    public Exam()
    {
        items = new ArrayList<DocumentItemApi>();
        clipboard = new ArrayList<DocumentItemApi>();
        format = new Format();
        titlegroup = new TitleGroup(this);
        headerfooter = new HeaderFooter();
        format.addObserver(this);
        isAnswerDoc = false;
    }
    
    /**
     * Instantiate all images in the exam by creating temp files.
     */
    public void instantiateImages()
    {
        // For all the items in the exam
        for(DocumentItemApi item : items)
        {
            item.createTempFile();
        }
        updated();
    }

    /**
     * Deletes all images that were created by this exam
     */
    public void cleanUp()
    {
        // For all the items in the exam
        for(DocumentItemApi item : items)
        {
            item.deleteTemp();
        }
    }
    
    /**
     * Updates when format is changed
     * @param observable the format class
     * @param args not used, can be null
     */
    @Override
    public void update(Observable observable, Object args)
    {
        // If the format class was updated
        if (observable instanceof FormatApi )
        {
            updated();
        }
    }
    
    /**
     * Method to alert observers of updates
     */
    public void updated()
    {
        // If in the process of bulk updating, don't notify the changes to the 
        // observer yet
        if ( !bulkUpdating )
        {
            setChanged();
            notifyObservers();
        }
    }
    
    /**
     * Toggles whether to display the exam as a regular exam or answer key
     */
    public void toggleAnswerDoc()
    {
        // If the exam is currently an answer key
        if (isAnswerDoc)
        {
            isAnswerDoc = false;
        }
        else
        {
            isAnswerDoc = true;
        }
    }

    /**
     * Tell whether the exam is currently being displayed as a regular exam or
     * an answer key.
     *
     * @return if the test is currently a answer key
     */
    public boolean getAnswerDoc()
    {
        return isAnswerDoc;
    }

    /**
     * Returns the Format object associated with this Exam.
     *
     * @return the Format object containing the exam formatting properties
     */
    public FormatApi getFormat()
    {
        return this.format;
    }
    
    /**
     * Returns the Header / Footer object associated with this Exam.
     *
     * @return the Header/Footer object containing this exam's header/footer.
     */    
    @Override
    public HeaderFooterApi getHeaderFooter()
    {
        return headerfooter;
    }

    /**
     * Returns the count of questions in the test. Only counts DocumentItems of
     * the Question subclass.
     *
     * @return The number of questions.
     */
    public int getNumQuestions()
    {
        int count = 0; //The count of question in the item list
        //For each item in the list of items
        for (DocumentItemApi item : items)
        {
            //If the item is a Question, increase the Question count
            if (item instanceof Question)
            {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Returns the number of items in the exam
     * @return the number of items in the exam
     */
    public int getNumItems()
    {
        return items.size();
    }

    /**
     * Returns an item at a specific index in the item list.
     *
     * @param index The item number to indicate which item to get.
     * @return The item at 'index'
     */
    public DocumentItemApi getItemAt(int index)
    {
        return items.get(index);
    }

    /**
     * Adds an item at a specific index.
     *
     * @param item The item to be inserted.
     * @param index The item number to indicate where to insert the item
     */
    public void addItemAt(DocumentItemApi item, int index)
    {
        items.add(index, item);
        updateList();
    }

    /**
     * Adds an item at the end of the test.
     *
     * @param item The item to be added.
     */
    public void addItem(DocumentItemApi item)
    {
        items.add(item);
        updateList();
    }

    /**
     * Moves an item from one sport in the list to another. Operates similar to
     * an insert in terms of indexes.
     *
     * @param indexOld The index of the item to be moved.
     * @param indexNew The new index that the item will be placed at.
     */
    public void moveItem(int indexOld, int indexNew)
    {
        DocumentItemApi item = items.remove(indexOld);
        items.add(indexNew, item);
        updateList();
    }

    /**
     * Removes an item from the list and adjusts indexes appropriately.
     *
     * @param index The index of the item to be moved.
     */
    public void removeItem(int index)
    {
        items.remove(index);
        updateList();
    }

    /**
     * Removes the items at the indexes in the array
     *
     * @param idx array of indexes to remove
     */
    public void removeItems(int[] idx)
    {
        Arrays.sort(idx);
        // for every index listed in idx remove the item from items
        for (int itr = idx.length - 1; itr > -1; itr--)
        {
            items.remove(idx[itr]);
        }
        updateList();
    }

    /**
     * Removes an item from the list and adjusts indexes appropriately. If the
     * item is not found, the new item is added to the end of the list
     *
     * @param toRemove The item in the current list to be removed
     * @param replaceWith The item to replace toRemove with
     */
    public void replaceItem(DocumentItemApi toRemove, DocumentItemApi replaceWith)
    {
        //The current index of the list to look at
        int index = 0;
        //Indicates whether toRemove has been found
        boolean itemFound = false;
        //While we are not at the end of the list and we havent found the item
        while (index < items.size() && !itemFound)
        {
            //If the current item is the item we are looking for
            if (items.get(index) == toRemove)
            {
                items.remove(index);
                items.add(index, replaceWith);
                itemFound = true;
            }
            index++;
        }
        //If we didn't find the item, add it to the end
        if (!itemFound)
        {
            this.addItem(replaceWith);
        }
        updateList();
    }

    /**
     * Updates the ordering of the list, including both QNum and Item number.
     * This method should be called after each change to the list.
     */
    public void updateList()
    {
        int questionNumber = 1;
        //array order: [FITB, FR, Match, MC, Ord, TF]
        int[] counts = {1, 1, 1, 1, 1, 1};
        //For each of the items in the list
        for (int index = 0; index < items.size(); index++)
        {
            //If the item is active and is a Question, assign it a number
            //Otherwise, remove the number from inactive questions
            if (items.get(index).isActive()
                    && items.get(index) instanceof Question)
            {
                getQuestionCount(items.get(index), counts);
                ((Question)items.get(index)).setActualQNum(questionNumber);
                questionNumber++;
            }
            // If the item at index is active
            else if (!items.get(index).isActive()
                    && items.get(index) instanceof Question)
            {
                ((Question) items.get(index)).setQNum(0);
            }
        }
        updated();
    }
    
    private void getQuestionCount(DocumentItemApi item, int[] counts)
    {
        //The item type is FillInTheBlank
        if( item.getAbbreviation().equals(kBundle.getString("FITB")) )
        {
            ((Question) item).setQNum(counts[0]);
            
            counts[0]++;
        }
        //The item type is FreeResponse
        else if( item.getAbbreviation().equals(kBundle.getString("FR")) )
        {
            ((Question) item).setQNum(counts[1]);
            counts[1]++;
        }
        //The item type is Matching
        else if( item.getAbbreviation().equals(kBundle.getString("MCH")) )
        {
            ((Question) item).setQNum(counts[2]);
            counts[2]++;
        }
        //The item type is MultipleChoice
        else if( item.getAbbreviation().equals(kBundle.getString("MC")) )
        {
            ((Question) item).setQNum(counts[kMCIndex]);
            counts[kMCIndex]++;
        }
        //The item type is Order
        else if( item.getAbbreviation().equals(kBundle.getString("ORD")) )
        {
            ((Question) item).setQNum(counts[kORDIndex]);
            counts[kORDIndex]++;
        }
        //The item type is TrueFalse
        else
        {
            ((Question) item).setQNum(counts[kTFIndex]);
            counts[kTFIndex]++;
        }   
    }


    /**
     * Randomizes the Items at the given indexes. Only the fields specified in
     * indexes are available as new positions for elements at other indexes. 
     * Randomizes with respect to sections
     *
     * @param indexes The indexes to randomize.
     * @param randomizeMc whether to randomize multiple choice answers
     * @param randomizeOrd whether to randomize order answers
     * @param randomizeMat whether to randomize matching choices
     */
    public void randomize(int[] indexes, boolean randomizeMc, 
            boolean randomizeOrd, boolean randomizeMat)
    {
        int[] sectionIndexes = generateSectionIndexes(indexes[0]);
        int curSection = indexes[0];
        // If no section indicies were generated, end
        if ( sectionIndexes == null)
        {
            return;
        }
        // If first section is smaller then the section the user selected
        if ( sectionIndexes[sectionIndexes.length - 1] < indexes[indexes.length - 1] )
        {
            // While the current section is within what the user selected
            while(curSection < indexes[indexes.length - 1] )
            {
                sectionIndexes = generateSectionIndexes(curSection);
                // If the section goes beyond what the user selected
                if (sectionIndexes[sectionIndexes.length - 1] >
                       indexes[indexes.length - 1])
                {
                    randomize(fillArray(sectionIndexes[0], 
                        indexes[indexes.length - 1]), randomizeMc, randomizeOrd,
                        randomizeMat);
                    curSection = Integer.MAX_VALUE;
                }
                else
                {
                    randomize(sectionIndexes, randomizeMc, randomizeOrd,
                        randomizeMat);
                    curSection = sectionIndexes[sectionIndexes.length - 1] + 1;
                }
            }
        }
        else
        {
            randomizeIndexes(indexes, randomizeMc, randomizeOrd, randomizeMat);
        }
    }
    
    
    /**
     * Randomizes the Items at the given indexes. Only the fields specified in
     * indexes are available as new positions for elements at other indexes. 
     * Does NOT respect sections
     *
     * @param indexes The indexes to randomize.
     * @param randomizeMc whether to randomize multiple choice answers
     * @param randomizeOrd whether to randomize order answers
     * @param randomizeMat whether to randomize matching choices
     */
    public void randomizeIndexes(int[] indexes, boolean randomizeMc, 
            boolean randomizeOrd, boolean randomizeMat)
    {
        ArrayList<DocumentItemApi> temp = new ArrayList<DocumentItemApi>();
        DriverApi driver = Lookup.getDefault().lookup(DriverApi.class);
        indexes = driver.removeDesignItemIndexes(indexes, this);
        boolean randomizeAnswer;
        //Add items at the given indexes to a temporary array
        for (int index = 0; index < indexes.length; index++)
        {
            //If a Question is randomizable, randomize the answers within it
            if (items.get(indexes[index] - index) instanceof Randomizable)
            {
                randomizeAnswer = items.get(indexes[index] - index) 
                        instanceof MultipleChoiceApi && randomizeMc;
                randomizeAnswer |= items.get(indexes[index] - index)
                        instanceof OrderApi && randomizeOrd;
                randomizeAnswer |= items.get(indexes[index] - index) 
                        instanceof MatchingApi && randomizeMat;
                // If the answer is to be randomized
                if ( randomizeAnswer )
                {
                    ((Randomizable) items.get(indexes[index] - index))
                            .randomizeAnswers();
                }
            }
            temp.add(items.get(indexes[index] - index));
            items.remove(indexes[index] - index);
        }
        //Choose a random item to put back into the original list
        for (int index = 0; index < indexes.length; index++)
        {
            int rand = random.nextInt(indexes.length - index);
            items.add(indexes[index], temp.get(rand));
            temp.remove(rand);
        }
        updateList();
    }
    
    
    /**
     * Randomizes the Items at the given indexes. Only the fields specified in
     * indexes are available as new positions for elements at other indexes.
     *
     * @param randomizeMc whether to randomize multiple choice answers
     * @param randomizeOrd whether to randomize order answers
     * @param randomizeMat whether to randomize matching choices
     */
    public void randomizeAnswers(boolean randomizeMc, 
            boolean randomizeOrd, boolean randomizeMat)
    {
        boolean randomizeAnswer;
        //For every item in the exam
        //For each item in the test, see if its answers are randomizable
        for( DocumentItemApi item: items )
        {
            //If a Question is randomizable, randomize the answers within it
            if (item instanceof Randomizable)
            {
                randomizeAnswer = item instanceof 
                        MultipleChoiceApi && randomizeMc;
                randomizeAnswer |= item instanceof
                        OrderApi && randomizeOrd;
                randomizeAnswer |= item instanceof 
                        MatchingApi && randomizeMat;
                // If the answer is to be randomized
                if ( randomizeAnswer )
                {
                    ((Randomizable)item)
                            .randomizeAnswers();
                }
            }
        }
        updated();
    }

    /**
     * Randomizes the items of the exam, excluding all DesignItems.
     * @param randomizeMc whether to randomize multiple choice answers
     * @param randomizeOrd whether to randomize order answers
     * @param randomizeMat whether to randomize matching choices
     */
    public void randomizeAll(boolean randomizeMc, boolean randomizeOrd, 
            boolean randomizeMat)
    {
        int examSize = getNumItems();
        int[] allIndices = new int[examSize];
        // For all the items in the exam
        for (int idxNum = 0; idxNum < examSize; idxNum++)
        {
            allIndices[idxNum] = idxNum;
        }
        randomize(allIndices, randomizeMc, randomizeOrd, randomizeMat);
    }
    
    private int[] fillArray(int start, int end)
    {
        int[] filled = new int[end - start + 1];
        // For all the numbers between start and end
        for (int num = start; num <= end; num++)
        {
            filled[num - start] = num;
        }
        return filled;
    }

    /**
     * Generates an array of the question indexes in the current list.
     *
     * @return An int array of question indexes.
     
    private int[] generateQuestionIndexes()
    {
        //the array of indexes of each question in the entire test
        int[] questionIndexes = new int[this.getNumQuestions()];
        //The current index being processed in the item ArrayList
        int itemsIndex = 0;
        //The index of the next Question to be added to questionIndexes
        int questionIndex = 0;
        //For each Question in items, place its index in questionIndexes
        for (DocumentItemApi item : items)
        {
            //If the item is a question, record its index
            if (item instanceof Question)
            {
                questionIndexes[questionIndex] = itemsIndex;
                questionIndex++;
            }
            itemsIndex++;
        }
        return questionIndexes;
    }*/

    /**
     * Returns the entire item list including both DesignItems and Questions.
     *
     * @return An ArrayList of DocumentItems in the current Exam.
     */
    public ArrayList<DocumentItemApi> getItems()
    {
        return items;
    }

    /**
     * Returns only the questions in the given test. DesignItems will not be
     * included.
     *
     * @return An ArrayList containing Question objects.
     */
    public ArrayList<Question> getQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        //For each item in the list
        for (DocumentItemApi item : items)
        {
            //If the item is a Question, add it to the new Question list
            if (item instanceof Question)
            {
                questions.add((Question) item);
            }
        }
        return questions;
    }

    /**
     * Sets a DocumentItem at a given index to a new, given DocumentItem
     *
     * @param item The new item to replace the item at the given index
     * @param index The index to put the new item at
     */
    public void setItemAt(DocumentItemApi item, int index)
    {
        DocumentItemApi oldItem = items.set(index, item);
        this.updateList();
    }
    
    /**
     * Get the title group instance for this exam.
     * @return This exam's title group instance
     */
    public TitleGroupApi getTitleGroup()
    {
        return this.titlegroup;
    }

    /**
     * Generate Exam
     *
     * @return Exam HTML
     */
    public String generateXhtmlExam()
    {
        boolean isLeftAlign;
        String middle;
        StringBuilder sb = new StringBuilder();  // For building html string
        sb.append(format.getHtmlBodyStart()); // setup html, css, head, body
        sb.append(format.getHtmlHeader(headerfooter));
        sb.append(format.getHtmlFooter(headerfooter));
        sb.append(this.getTitleGroup().getTitleGroupHtml()); //setup title group
        // Start list for all question
        sb.append("<ol class=\"exam_list\">");
        //Iterate through document items and add their HTML
        for (DocumentItemApi di : items)
        {
            // If the document item is active
            if (di.isActive())
            {
                isLeftAlign = (di instanceof DesignItem) && 
                        !(di instanceof NumberingRestartApi);
                // If the document item is a design element and not num restart
                if (isLeftAlign )
                {
                    sb.append("<p class=\"leftalign\">");
                    middle = (di.getHTML(format, isAnswerDoc));
                    sb.append(middle);
                    sb.append("</p>");
                }
                // if the item is a question type
                else if(di instanceof Question)
                {
                    sb.append("<li class=\"question_item\"");
                    // If the "keep together" option is on
                    if (di.isKeepTogether())
                    {
                        sb.append(" id=\"keep-together\" ");
                    }
                    sb.append("><div style=\"position:relative; width:93%\">");
                    middle = (di.getHTML(format, isAnswerDoc));
                    sb.append(middle);
                    sb.append("</div></li>");
                }
                else
                {
                    middle = di.getHTML(format, isAnswerDoc);
                    sb.append(middle);
                }
            }
        }
        sb.append("</ol>"); // Close question list
        sb.append(format.getHtmlBodyEnd());
        String newString = sb.toString();
        newString = newString.replace("face=\"Times New Roman\"",
                "class=\"default_font\"");
        newString = newString.replace("face=\"monospace\"",
                "class=\"monospace_font\"");
        return newString;
    }


    private Element createCategoryElem(Document doc)
    {
        Element category, categoryText;
        category = doc.createElement("category");
        categoryText = doc.createElement("text");
        categoryText.setTextContent("Imported Exam: " +
                getTitleGroup().getTitle());
        category.appendChild(categoryText);
        return category;
    }


    /**
     * Generates Moodle XML representation of the exam
     * @param doc Document to populate with exam data
     */
    public void generateMoodle(Document doc)
    {
        Element quizRoot = doc.createElement("quiz"), moodleItem, name, 
                nameText;
        int questionNumber = 1;

        // Add category
        quizRoot.appendChild(createCategoryElem(doc));

        // For each document item, serialize the item and append to quiz element
        for (DocumentItemApi item : items)
        {
            moodleItem = item.serializeMoodle(doc);

            // If the item is to be added to the quizRoot (not null)
            if (moodleItem != null)
            {
                name = doc.createElement("name");
                nameText = doc.createElement("text");

                nameText.setTextContent("Q" + (questionNumber++) + 
                        ": " + item.getType());
                name.appendChild(nameText);
                
                moodleItem.appendChild(name);
                quizRoot.appendChild(moodleItem);
            }
        }
        doc.appendChild(quizRoot);
    }


    /**
     * Calculates the total marks of every active Questions.
     *
     * @return The total marks
     */
    public int getTotalMarks()
    {
        int markTotal = 0;
        //Get the marks from each question and add to total
        for (int index = 0; index < items.size(); index++)
        {
            //If the item is active and is a Question, assign it a number
            if (items.get(index).isActive()
                    && items.get(index) instanceof Question)
            {
                markTotal += ((Question) items.get(index)).getMarks();
            }
        }
        return markTotal;
    }

    /**
     * Moves an entire contiguous group to a specific destination.
     *
     * @param startIndex The index of the first item of the group
     * @param endIndex The index of the last item of the group ( inclusive )
     * @param destination The index to move the group to
     *
     */
    public void moveGroup(int startIndex, int endIndex, int destination)
    {
        //The destination index cannot be located within the group
        assert (startIndex > destination || endIndex < destination);
        ArrayList<DocumentItemApi> group = new ArrayList<DocumentItemApi>();
        //Checks if destination should be changed based on indicies
        if (destination > endIndex)
        {
            destination -= endIndex - startIndex;
        }
        //For each item in between indexes, remove it and place in group
        for (int index = startIndex; index <= endIndex; index++)
        {
            group.add(items.remove(startIndex));
        }
        //For each element in group, insert at destination.
        for (int index = group.size() - 1; index >= 0; index--)
        {
            items.add(destination, group.get(index));
        }
        updateList();
    }

    /**
     * Given an index of a SectionTitle, generates a list of indexes for all
     * items within that section.
     *
     * @param titleIndex The index of the SectionTitle item for a given section
     * @return The array of indexes in the section; null if section is empty
     */
    public int[] generateSectionIndexes(int titleIndex)
    {
        //The array to store the index of items in the section
        int[] indexes;
        //The index of the beginning of the section
        int start = titleIndex;
        //The index of the end of the section
        int end = titleIndex;
        //A count used to fill the indexes array
        int count = 0;
        //Indicates whether the last item was the end of the ArrayList items
        boolean foundEnd = false;
        //The item being processed to determine if it is the end of the section
        DocumentItemApi currentItem = items.get(titleIndex);

        //Ensure that the section is not the last item in items
        if (titleIndex != items.size() - 1)
        {
            end++;
            currentItem = items.get(end);
        }
        //Determine the index of the end of the section
        while (!(currentItem instanceof SectionTitle) && !foundEnd)
        {
            //Process the next item if we are not at the last item of the list
            if (end != items.size() - 1)
            {
                end++;
                currentItem = items.get(end);
            }
            else
            {
                foundEnd = true;
            }
        }

        //If the end wasn't found, subtract 1 from end to make it accurate
        if (!foundEnd)
        {
            end--;
        }
        //initialize indexes to the proper size
        if (end - start > 0)
        {
            indexes = new int[end - start];
        }
        else
        {
            indexes = null;
        }
        //fill indexes with the given elements
        for (start = start + 1; start <= end; start++)
        {
            indexes[count] = start;
            count++;
        }

        return indexes;
    }

    /**
     * Randomizes the elements within a section given the SectionTitle index.
     *
     * @param titleIndex The index of the SectionTitle item at the beginning of
     * the section
     */
    public void randomizeSection(int titleIndex)
    {
        int[] sectionIndexes = generateSectionIndexes(titleIndex);
        this.randomize(sectionIndexes, true, true, true);
        updateList();
    }

    /**
     * Swaps two elements in the list.
     *
     * @param index1 The index of the first item to be swapped
     * @param index2 The index of the second item to be swapped
     */
    public void swap(int index1, int index2)
    {
        DocumentItemApi item2 = items.get(index2);
        this.setItemAt(items.get(index1), index2);
        this.setItemAt(item2, index1);
        updateList();
    }

    /**
     * Copy the selected questions
     *
     * @param toCopy the indexes that are to be copied
     */
    public void copy(int[] toCopy)
    {
        // If there are indexes to be copied
        if (toCopy.length > 0)
        {
            clipboard.clear();
            // For every item on that is to be copied
            for (int row : toCopy)
            {
                clipboard.add(getItemAt(row));
            }
        }
    }

    /**
     * Cut the selected questions Preconditions: There should be rows selected
     * @param toCut Questions to be cut
     */
    public void cut(int[] toCut)
    {
        // If there are items selected ( user doesn't want to cut nothing )
        if (toCut.length > 0)
        {
            clipboard.clear();
            // For every row that is selected
            for (int row : toCut)
            {
                clipboard.add(getItemAt(row));
            }
            removeItems(toCut);
        }
        updateList();
    }

    /**
     * Paste the selected questions If a singular question is selected, places
     * the pasted question under the selection. If multiple questions are
     * selected, replaces them with pasted contents.
     *
     * @param index the index to paste the clipboards contents to. If the index
     * is invalid, it is just pasted at the top of the exam Preconditions:
     * @return an array of indices pointing to the items that were pasted
     */
    public int[] paste(int index)
    {
        int pasteTo;
        int examSize = items.size();

        int[] pastedRows = new int[clipboard.size()];
        // If the index is not a valid index
        if (index < 0 || index > items.size())
        {
            pasteTo = Integer.MAX_VALUE;
        }
        else
        {
            pasteTo = index + 1;
        }

        // If the user wants to paste the information at the end of the exam
        if (pasteTo >= examSize)
        {
            // For all items that were copied, add them at the end of the test
            for (int item = 0; item < clipboard.size(); item++)
            {
                addItem(clipboard.get(item).clone());
                pastedRows[item] = item + examSize;
            }
        }
        else
        {
            // For all items that were copied, add them at index 'pasteTo'
            for (int item = clipboard.size() - 1; item >= 0; item--)
            {
                addItemAt(clipboard.get(item).clone(), pasteTo);
                pastedRows[item] = item + pasteTo;
            }
        }

        updateList();
        return pastedRows;
    }

    /**
     * Accessor for path
     * @return path
     */
    @Override
    public String getPath()
    {
        return path;
    }
    
    /**
     * Setter for path
     * @param nPath the new path
     */
    @Override
    public void setPath(String nPath)
    {
        path = nPath;
    }

    /**
     * Resets the current exam to a new Exam
     */
    @Override
    public void newExam()
    {
        items = new ArrayList<DocumentItemApi>();
        clipboard = new ArrayList<DocumentItemApi>();
        format.newFormat();
        titlegroup = new TitleGroup(this);
        headerfooter = new HeaderFooter();
        isAnswerDoc = false;
        random = new Random();
        selected = "";
        path = null;
        updated();
    }
    
    /**
     * Resets the current exam to a different exam
     * @param nExam the exam to overwrite the current exam with
     */
    @Override
    public void newExam(ExamApi nExam)
    {
        // If the new exam is not null
        if (nExam != null)
        {
            Exam toBe = (Exam) nExam;
            items = toBe.items;
            clipboard = toBe.clipboard;
            format.newFormat(toBe.getFormat());
            titlegroup = toBe.titlegroup;
            isAnswerDoc = toBe.isAnswerDoc;
            random = toBe.random;
            headerfooter = toBe.headerfooter;
            selected = toBe.selected;
            path = toBe.path;
            updated();
        }
    }
    /**
     * Called when ever numerous updates are being done to the exam
     * @param start whether the update is starting or ending 
     */
    public void bulkUpdate(boolean start)
    {
        bulkUpdating = start;
        // If the buldUpdate is finished, update everything
        if ( bulkUpdating == kEnd)
        {
            updated();
        }
    }
    
    /**
     * Returns a list of unique levels as Strings.
     * @return An array of levels represented by Strings
     */
    public String[] getLevels()
    {
        ArrayList<String> catArray = new ArrayList<String>();
        //For every item in the array, get its level
        for(DocumentItemApi item : items)
        {
            ////If the current item's level is unique, record the level
            if( !catArray.contains(item.getLevel()) )
            {
                catArray.add(item.getLevel());
            }
        }
        return catArray.toArray(new String[catArray.size()]);
    }
    
    /**
     * Returns a list of unique categories as Strings.
     * @return An array of categories represented by Strings
     */
    public String[] getCategories()
    {
        ArrayList<String> catArray = new ArrayList<String>();
        //For every item in the array, get its category
        for(DocumentItemApi item : items)
        {
            ////If the current item's category is unique, record the category
            if( !catArray.contains(item.getCategory()) )
            {
                catArray.add(item.getCategory());
            }
        }
        return catArray.toArray(new String[catArray.size()]);
    }
    
    /**
     * Return indexes of items in a specific category.
     * @param category The item category to index
     * @return An array of item indexes in that given category
     */
    public int[] getCategoryIndexes(String category)
    {
        ArrayList<Integer> indList = new ArrayList<Integer>();
        int index = 0;
        int[] intIndexes; 
        //For ever item in the exam, check the category
        for(DocumentItemApi item : items)
        {
            //If the current item's category matches, record its index
            if( item.getCategory().equals(category) ) 
            {
                indList.add(index);
            }
            index++;
        }
        
        intIndexes = new int[indList.size()];
        //Copy all value to new int array
        for(index = 0; index < indList.size(); index++)
        {
            intIndexes[index] = indList.get(index);
        }
        return intIndexes;
    }
    
    /**
     * Return indexes of items of a specific level.
     * @param level The item category to index
     * @return An array of item indexes of the given level
     */
    public int[] getLevelIndexes(String level)
    {
        ArrayList<Integer> indList = new ArrayList<Integer>();
        int index = 0;
        int[] intIndexes; 
        //For ever item in the exam, check the category
        for(DocumentItemApi item : items)
        {
            //If the current item's category matches, record its index
            if( item.getLevel().equals(level) )
            {
                indList.add(index);
            }
            index++;
        }
        
        intIndexes = new int[indList.size()];
        //Copy all value to new int array
        for(index = 0; index < indList.size(); index++)
        {
            intIndexes[index] = indList.get(index);
        }
        return intIndexes;
    }
    
    /**
     * Combine getLevel indexes and getCategoryIndexes to create one single
     * array created by the intersection of the ones generated the other two
     * methods.
     * @param level The level to filter
     * @param category The category to filter
     * @param filterBoth determines whether to filter 'both' or 'either'
     * @return A sorted array containing some number of unique indexes
     */
    public int[] getLevelAndCategoryIndexes(String[] level, String[] category, 
            boolean filterBoth)
    {
        int[] indList;
        // If the user would like to filter both filter and category
        if ( filterBoth )
        {
            indList = filterBothLevelCat(level, category);
        }
        else 
        {
            indList = filterEitherLevelCat(level, category);
        }
        
        return indList;     
    }
    
    private int[] filterBothLevelCat(String[] level, 
            String[] category)
    {
        int[] levArray;
        int[] catArray;
        int[] tempArray;
        
        ArrayList<Integer> indList = new ArrayList<Integer>();
        //For every index in the levArray, compare it to all catArray indexes
        for(int levIndex = 0; levIndex < level.length; levIndex++)
        {
            //For every index in the catArray, compare it to levArray curr index
            for(int catIndex = 0; catIndex < category.length; catIndex++)
            {
                levArray = getLevelIndexes(level[levIndex]);
                catArray = getCategoryIndexes(category[catIndex]);
                tempArray = intersectIntArrays(levArray, catArray);
                //Add all items in tempArray to the list
                for(int index = 0; index < tempArray.length; index++)
                {
                    indList.add(tempArray[index]);
                }
            }
        }
        int[] intIndexes = new int[indList.size()];
        //Copy the values in indList over to the array being returned
        for(int index = 0; index < indList.size(); index++)
        {
            intIndexes[index] = indList.get(index);
        }
        return intIndexes;
    }
    
    private int[] filterEitherLevelCat(String[] level, 
            String[] category)
    {
        int[] tempArray;
        int[] filteredArray = {};
        //For every index in the levArray, compare it to all catArray indexes
        for(String levelStr: level)
        {
            tempArray = getLevelIndexes(levelStr);
            filteredArray = unionIntArrays(filteredArray, tempArray);
        }
        // For all the categories
        for(String catStr: category)
        {
            tempArray = getCategoryIndexes(catStr);
            filteredArray = unionIntArrays(filteredArray, tempArray);
        }
        return filteredArray;
    }
    
    /**
     * Turns all items at indexes with the given array to active, and all others
     * inactive.
     * @param indexes The indexes of items to set to active
     */
    public void filterActive(int[] indexes)
    {
        ArrayList<Integer> intList = new ArrayList<Integer>();
        //copy indexes over to intList
        for(int index = 0; index < indexes.length; index++)
        {
            intList.add(indexes[index]);
        }
        int index = 0;
        //For ever item in the exam, set its activity
        for(DocumentItemApi item : items)
        {
            //If the current item's index is found, set it to active
            if( intList.contains(index) )
            {
                item.setActive(true);
            }
            else
            {
                item.setActive(false);
            }
            index++;
        }
        updated();
    }
    
    /**
     * Intersections the values within two int arrays.
     * @param first The first array of ints
     * @param second The second array of ints
     * @return and Array containing only the indexes in both arrays
     */
    public int[] intersectIntArrays(int[] first, int[] second)
    {
        ArrayList<Integer> indList = new ArrayList<Integer>();
        int[] intIndexes;
        //For every index in the levArray, compare it to all catArray indexes
        for(int firstIndex = 0; firstIndex < first.length; firstIndex++)
        {
            //For every index in the catArray, compare it to levArray curr index
            for(int secIndex = 0; secIndex < second.length; secIndex++)
            {
                //If the two value are equal, add to new array
                if( first[firstIndex] == second[secIndex] )
                {
                    indList.add(first[firstIndex]);
                }
            }
        }
        
        intIndexes = new int[indList.size()];
        //Copy all value to new int array
        for(int index = 0; index < indList.size(); index++)
        {
            intIndexes[index] = indList.get(index);
        }
        
        
        return intIndexes;
    }
    
     /**
     * Unions the values within two int arrays.
     * @param first The first array of ints
     * @param second The second array of ints
     * @return and Array containing only the indexes in both arrays
     */
    public int[] unionIntArrays(int[] first, int[] second)
    {
        int[] union;
        Object[] arr;
        int size;
        Set<Integer>  set = new TreeSet<Integer>();
        // For all the items in the first array
        for(int num: first)
        {
            set.add(num);
        }
        // For all the items in the second array
        for(int num: second)
        {
            set.add(num);
        }
        arr = set.toArray();
        size = set.size();
        union = new int[size];
        // For all the items in the unioned set
        for(int idx = 0; idx < size; idx++)
        {
            union[idx] = (Integer)arr[idx];
        }
        return union;
    }

    /**
     * Checks if the exact same (not just a deep copy) item exists in the exam
     * @param item the item to search for
     * @return whether item is in the exam
     */
    @Override
    public boolean containsReference(DocumentItemApi item)
    {
        // For all the items in the exam
        for (DocumentItemApi itr: items)
        {
            // If the item has been matched
            if (itr == item)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the total number of items with the same type as the given item.
     * @param item The item to compare types with
     * @return the total number of items with the same type
     */
    public int getNumType(QuestionApi item)
    {
        int total = 0;
        //For every item, compare the types - add one to total if they match
        for (DocumentItemApi itr: items)
        {
            // If the item has been matched
            if (itr.getType().equals(item.getType()))
            {
                total++;
            }
        }
        return total;
    }
}
