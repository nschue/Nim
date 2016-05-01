package com.example.cam.nim;
import java.util.ArrayList;
import java.util.Random;
import android.util.Log;

/**
 * It should be noted that the "impossible" difficulty may not actually be
 * impossible, depending on the total number of rows and which player gets
 * to go first.
 * <p>
 * The AI is created at the very start of the game with
 * "private AI aiName = new AI(difficulty)" and when it's the AI's turn,
 * aiName.calculateNextMove(remainingDots) will return an ArrayList that shows
 * its next move (index 0 is the row choice, every proceeding index is a dot
 * choice).
 * <p>
 * I have NOT tested this yet, so there will almost certainly be bugs.
 * @author Kendall Pruitt
 */
public class AI
{
    /**
     * A random number used for simulations. Not truly random as it uses a fixed
     * seed, but this can easily be changed.
     */
    private static Random rndm = new Random(616);
    /**
     * An ArrayList of 'rows' which are themselves ArrayLists of 'dots'
     * represented with boolean values.
     */
    private ArrayList<ArrayList<Boolean>> remainingDots;
    /**
     * An ArrayList of Integers where each Integer represents the number of
     * remaining dots in its respective row.
     */
    private ArrayList<Integer> dotCountPerRow;
    /**
     * An ArrayList of Integers where each Integer represents the number of
     * total dots in its respective row.
     */
    private ArrayList<Integer> dotCountPerRow2;
    /**
     * The number of rows that contain at least one dot.
     */
    private Integer populatedRowCount;
    /**
     * A floating point value from 0 to 1 with 1 being the most difficult
     * setting. If set to 0, the AI will choose randomly 100% of the time.
     */
    private Double difficulty;

    //Constructors
    /**
     * Creates a new AI object with a default difficulty of 1.0 (impossible).
     */
    public AI()
    {
        this(1.0);
    }
    /**
     * Creates a new AI object with the specified difficulty.
     * @param difficulty
     */
    public AI(Double difficulty)
    {
        setDifficulty(difficulty);
    }
    
    //Other methods
    /**
     * Determines the AI's next move based on current difficulty.
     * If victory cannot be guaranteed with the given configuration, it chooses
     * randomly in the hope that the player will make a mistake.
     * @param  remainingDots an ArrayList of 'rows' which are themselves
     *         ArrayLists of 'dots' represented with boolean values
     * @return a one-dimentional ArrayList consisting of the indices of the
     *         dots that the AI has chosen
     */
    public ArrayList<Integer> calculateNextMove(ArrayList<ArrayList<Boolean>> remainingDots)
    {
        setRemainingDots(remainingDots);
        ArrayList<Integer> fullMove = new ArrayList<>();
        Integer parity = 0;
        Integer dotsChoice = -1;
        Integer rowChoice = -1;
        for (Integer dotCount : dotCountPerRow)
        {
            parity = parity ^ dotCount;
        }
        Integer precedingDotCount = 0;
        for(int rowIndex = 0; rowIndex < dotCountPerRow.size(); rowIndex++)
        {
            if((parity ^ dotCountPerRow.get(rowIndex)) < dotCountPerRow.get(rowIndex))
            {
                dotsChoice = dotCountPerRow.get(rowIndex) - (parity ^ dotCountPerRow.get(rowIndex));
                rowChoice = rowIndex;
                rowIndex = dotCountPerRow.size();
            }
        }
        if(rowChoice < 0 || madeMistake())
        {
            // If victory is uncertain or the AI makes a 'mistake', choose randomly
            rowChoice = rndm.nextInt(populatedRowCount);
            for(int i = 0; i <= rowChoice; i++)
            {
                if(dotCountPerRow.get(i) == 0)
                {
                    ++rowChoice;
                }
            }
            dotsChoice = rndm.nextInt(dotCountPerRow.get(rowChoice))+1;
        }
        // fullMove.add(rowChoice);
        for(int rowIndex = 0; rowIndex < rowChoice; rowIndex++)
        {
            precedingDotCount = precedingDotCount + dotCountPerRow2.get(rowIndex);
        }
        int dotIndex = 0;
        for(int dotCount = 0; dotCount < dotsChoice; dotCount++)
        {
            while(!remainingDots.get(rowChoice).get(dotIndex))
            {
                dotIndex++;
            }
            fullMove.add(dotIndex + precedingDotCount);
            dotIndex++;
        }
        return fullMove;
    }


    /**
     * generates a boolean value based on the difficulty to determine whether
     * or not the AI makes a mistake
     * @return true if the AI makes a 'mistake'
     */
    public Boolean madeMistake()
    {
        return madeMistake(getDifficulty());
    }
    /**
     * generates a boolean value based on the difficulty to determine whether or
     * not the AI makes a mistake
     * @param  difficulty
     * @return true if the AI makes a 'mistake'
     */
    public Boolean madeMistake(Double difficulty)
    {
        return ((100 * difficulty) < (rndm.nextInt(100) + 1));
    }


    //Getters
    /**
     * @return remainingDots
     */
    public ArrayList<ArrayList<Boolean>> getRemainingDots()
    {
        return remainingDots;
    }
    /**
     * @return dotCountPerRow
     */
    public ArrayList<Integer> getDotCountPerRow()
    {
        return dotCountPerRow;
    }
    /**
     * @return populatedRowCount
     */
    public Integer getPopulatedRowCount()
    {
        return populatedRowCount;
    }
    /**
     * @return difficulty
     */
    public Double getDifficulty()
    {
        return difficulty;
    }


    //Setters
    /**
     * @param remainingDots
     */
    public void setRemainingDots(ArrayList<ArrayList<Boolean>> remainingDots)
    {
        Integer count, count2;
        Integer populatedRowCount = 0;
        ArrayList<Integer> dotCountPerRow = new ArrayList();
        ArrayList<Integer> dotCountPerRow2 = new ArrayList();
        this.remainingDots = remainingDots;
        for(ArrayList<Boolean> row : remainingDots)
        {
            count = 0;
            count2 = 0;
            for(Boolean dot : row)
            {
                if(dot)
                {
                    count++;
                }
                count2++;
            }
            dotCountPerRow.add(count);
            dotCountPerRow2.add(count2);
            if(count > 0)
            {
                populatedRowCount++;
            }
        }
        setPopulatedRowCount(populatedRowCount);
        setDotCountPerRow(dotCountPerRow);
        this.dotCountPerRow2 = dotCountPerRow2;
    }
    /**
     * @param dotCountPerRow
     */
    public void setDotCountPerRow(ArrayList<Integer> dotCountPerRow)
    {
        this.dotCountPerRow = dotCountPerRow;
    }
    /**
     * @param populatedRowCount
     */
    public void setPopulatedRowCount(Integer populatedRowCount)
    {
        this.populatedRowCount = populatedRowCount;
    }
    /**
     * @param difficulty
     */
    public void setDifficulty(Double difficulty)
    {
        if(difficulty < 0)
        {
            // error: difficulty can't be set lower than 0.
            this.difficulty = 0.0;
        }
        else if(difficulty > 1)
        {
            // error: difficulty can't be set higher than 1.
            this.difficulty = 1.0;
        }
        else
        {
            this.difficulty = difficulty;
        }
    }
}