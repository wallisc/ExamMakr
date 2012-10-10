package qsolutions.api;

import java.util.ArrayList;
import java.util.Observer;
import org.w3c.dom.Document;

/**
 * The Exam interface for use with LookUp. Only contains methods needed
 * externally.
 * @author Ryan Dollahon
 */
public interface ExamApi
{
    /**
     * Adds an item at the end of the test.
     *
     * @param toAdd The item to be added.
     */
    void addItem(DocumentItemApi toAdd);
    
    /**
     * Adds an item at a specific index.
     *
     * @param item The item to be inserted.
     * @param index The item number to indicate where to insert the item
     */
    public void addItemAt(DocumentItemApi item, int index);
    
    /**
     * Checks if items has the instance of item in it
     * @param item the item to check for a reference to
     * @return true if items contains a reference to item, otherwise false
     */
    public boolean containsReference(DocumentItemApi item);
    
    /**
     * Copy the selected questions
     *
     * @param toCopy the indexes that are to be copied
     */
    void copy(int[] toCopy);
    
    /**
     * Cut the selected questions Preconditions: There should be rows selected
     * 
     * @param toCut Questions to be cut
     */
    void cut(int[] toCut);
    
    /**
     * Generate XHTML representation of the Exam
     *
     * @return Exam HTML
     */
    String generateXhtmlExam();
    
    /**
     * Tell whether the exam is currently being displayed as a regular exam or
     * an answer key.
     *
     * @return if the test is currently a answer key
     */
    boolean getAnswerDoc();
    
    /**
     * Returns an item at a specific index in the item list.
     *
     * @param index The item number to indicate which item to get.
     * @return The item at 'index'
     */
    DocumentItemApi getItemAt(int index);
    
    /**
     * Returns the entire item list including both DesignItems and Questions.
     *
     * @return An ArrayList of DocumentItems in the current Exam.
     */
    ArrayList<DocumentItemApi> getItems();
    
    /**
     * Return the number of items in the exam
     * 
     * @return the number of items in exam
     */
    int getNumQuestions();
    
    /**
     * Returns the Format object associated with this Exam.
     *
     * @return the Format object containing the exam formatting properties
     */
    FormatApi getFormat();
    
    /**
     * Returns the Title Group object associated with this Exam.
     *
     * @return the Title Group object containing this exam's title group.
     */
    TitleGroupApi getTitleGroup();
    
    /**
     * Returns the Header / Footer object associated with this Exam.
     *
     * @return the Header/Footer object containing this exam's header/footer.
     */
    HeaderFooterApi getHeaderFooter();
    
    /**
     * Get the path of the file
     * 
     * @return path of the file
     */
    String getPath();
    
    /**
     * Moves an entire contiguous group to a specific destination.
     *
     * @param startIndex The index of the first item of the group
     * @param stopIndex The index of the last item of the group ( inclusive )
     * @param destination The index to move the group to
     */
    void moveGroup(int startIndex, int stopIndex, int destination);
    
    /**
     * Instantiates a new exam
     */
    void newExam();
    
    /**
     * Instantiates a new exam
     * @param nExam Api to be used in exam creation
     */
    void newExam(ExamApi nExam);
    
    /**
     * Paste the selected questions If a singular question is selected, places
     * the pasted question under the selection. If multiple questions are
     * selected, replaces them with pasted contents.
     *
     * @param index the index to paste the clipboards contents to. If the index
     * is invalid, it is just pasted at the top of the exam Preconditions:
     * @return an array of indices pointing to the items that were pasted
     */
    int[] paste(int index);
    
    /**
     * Randomizes the Items at the given indexes. Only the fields specified in
     * indexes are available as new positions for elements in at other indexes.
     *
     * @param rows The indexes to randomize.
     * @param randomizeMc whether to randomize multiple choice answers
     * @param randomizeOrd whether to randomize order answers
     * @param randomizeMat whether to randomize matching choices
     */
    void randomize(int[] rows, boolean randomizeMc, boolean randomizeOrd,
            boolean randomizeMat);
    
    /**
     * Randomizes the items of the exam, excluding all DesignItems.
     * @param randomizeMc whether to randomize multiple choice answers
     * @param randomizeOrd whether to randomize order answers
     * @param randomizeMat whether to randomize matching choices
     */
    void randomizeAll(boolean randomizeMc, boolean randomizeOrd,
            boolean randomizeMat);
    
     /**
     * Randomizes the Items at the given indexes. Only the fields specified in
     * indexes are available as new positions for elements at other indexes.
     *
     * @param randomizeMc whether to randomize multiple choice answers
     * @param randomizeOrd whether to randomize order answers
     * @param randomizeMat whether to randomize matching choices
     */
    public void randomizeAnswers(boolean randomizeMc, 
            boolean randomizeOrd, boolean randomizeMat);
    
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
            boolean randomizeOrd, boolean randomizeMat);
    
    /**
     * Removes an item from the list and adjusts indexes appropriately.
     *
     * @param index The index of the item to be moved.
     */
    void removeItem(int index);
    
    /**
     * Removes the items at the indexes in the array
     *
     * @param indexes array of indexes to remove
     */
    void removeItems(int[] indexes);
    
    /**
     * Removes an item from the list and adjusts indexes appropriately. If the
     * item is not found, the new item is added to the end of the list
     *
     * @param toRemove The item in the current list to be removed
     * @param toAdd The item to replace toRemove with
     */
    void replaceItem(DocumentItemApi toRemove, DocumentItemApi toAdd);
    
    /**
     * Set the path of the exam file
     * 
     * @param nPath Path of the exam file
     */
    void setPath(String nPath);
    
    /**
     * Toggles whether to display the exam as a regular exam or answer key
     */
    void toggleAnswerDoc();
    
    /**
     * Updates the ordering of the list, including both QNum and Item number.
     * This method should be called after each change to the list.
     */
    void updateList();
    
    /**
     * Adds an observer
     * @param obs Observer to be added
     */
    void addObserver(Observer obs);
    
    /**
     * Returns the number of items in the exam
     * 
     * @return the number of items in the exam
     */
    int getNumItems();
    
    /**
     * Called when ever numerous updates are being done to the exam
     * @param start whether the update is starting or ending 
     */
    public void bulkUpdate(boolean start);
    
    /**
     * Returns a list of unique levels as Strings.
     * @return An array of levels represented by Strings
     */
    public String[] getLevels();
    
    /**
     * Returns a list of unique categories as Strings.
     * @return An array of categories represented by Strings
     */
    public String[] getCategories();
    
    /**
     * Combine getLevel indexes and getCategoryIndexes to create one single
     * array created by the intersection of the ones generated the other two
     * methods.
     * @param level The level to filter
     * @param category The category to filter
     * @param filterBoth whether to filter both or either
     * @return A sorted array containing some number of unique indexes
     */
    int[] getLevelAndCategoryIndexes(String[] level, String[] category,
            boolean filterBoth);
    
    /**
     * Return indexes of items in a specific category.
     * @param category The item category to index
     * @return An array of item indexes in that given category
     */
    public int[] getCategoryIndexes(String category);
    
    /**
     * Return indexes of items of a specific level.
     * @param level The item category to index
     * @return An array of item indexes of the given level
     */
    public int[] getLevelIndexes(String level);
    
    /**
     * Turns all items at indexes with the given array to active, and all others
     * inactive.
     * @param indexes The indexes of items to set to active
     */
    public void filterActive(int[] indexes);
    
    /**
     * Intersections the values within two int arrays.
     * @param first The first array of ints
     * @param second The second array of ints
     * @return and Array containing only the indexes in both arrays
     */
    public int[] intersectIntArrays(int[] first, int[] second);
    
    /**
     * Get the total marks (points) for the exam
     * @return total points
     */
    public int getTotalMarks();
    /**
     * Instantiate all images in the exam by creating temp files.
     */
    public void instantiateImages();

    /**
     * Generates Moodle XML representation of the exam
     * @param doc Document to populate with exam data
     */
    public void generateMoodle(Document doc);
    /**
     * Gets the total number of items with the same type as the given item.
     * @param item The item to compare types with
     * @return the total number of items with the same type
     */
    public int getNumType(QuestionApi item);
    
    /**
     * Deletes all images that were created by this exam
     */
    public void cleanUp();
}
