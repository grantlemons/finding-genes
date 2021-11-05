import edu.duke.*;

public class Gene
{
    // Test cases to determine if the algorithm does as required
    public void test()
    {
        getGenome("ATGxxxTAG"); //Normal w/ TAG
        getGenome("ATGxxxTAA"); //Normal w/ TAA
        getGenome("ATGxxxTGA"); //Normal w/ TGA
        getGenome("xxxATGxxxTGA"); //Leading 'x's
        getGenome("xxxATGxxxTGAxxTAA"); //Extraneous End Codon
        getGenome("TAG"); //Only End Codon
        getGenome("ATG"); //Only Start Codon
        getGenome("xx"); //No Codon
        getGenome(""); //Empty string
        getGenome("ATGxxTAG"); //Incorrect Spacing
        getGenome("ATGxxxTAG   ATGxxxTAA"); //Two seperate genomes
        getGenome("ATGxxxTAG ATGxxxTAA"); //Two seperate genomes w/ spacing not multiple of three
        getGenome("xxATGxx"); //end - stop % 3 == 0
        getGenome("ATGxxxTAATGxxx"); //Combined start and stop codons
    }
    
    // Loads the sense strand file, parses it, and changes to upper case
    public String loadFile()
    {
        FileResource file = new FileResource("sensestrand.txt");
        
        // Use regex to remove whitespace
        // Change to upper case
        return file.asString().replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    // Method to find the start and stop codons, and print everything in between
    public void getGenome(String sequence)
    {
        boolean foundGenome = false;
        int pos = 0;
        
        while (sequence.length()-1 > pos)
        {
            int startIndex = sequence.indexOf("ATG", pos);

            if (startIndex >= 0)
            {
                // Finds the first valid stop codon after the start codon
                int myEnd = 0;
                myEnd = setEnd(startIndex, "TAA", sequence, myEnd);
                myEnd = setEnd(startIndex, "TAG", sequence, myEnd);
                myEnd = setEnd(startIndex, "TGA", sequence, myEnd);

                if (myEnd != 0)
                {
                    String subsequence = sequence.substring(startIndex, myEnd+3);
                    
                    System.out.println(startIndex+1+".."+(myEnd+2+1)+": "+subsequence);
                    printAntiSense(startIndex, myEnd, subsequence, sequence.length());

                    foundGenome = true;
                    pos = myEnd+3;
                }
                else
                {
                    return;
                }
            }
            else
            {
                return;
            }
        }
        if (!foundGenome)
        {
            System.out.println("No genomes found");
        }
    }

    // Finds the end codon by looping through all possible end codons until a valid one if found
    private int setEnd(int sIndex, String searchString, String sequence, int myEnd)
    {
        int fIndex = sIndex;
        
        // Loops through all possible end codons of the given type until one is found
        while (fIndex != -1 && fIndex < sequence.length())
        {
            fIndex = sequence.indexOf(searchString, fIndex+1);
            
            // Checks to make sure the end codon is valid
            if (((fIndex-sIndex) % 3 == 0 && fIndex != -1) && (myEnd == 0 || fIndex < myEnd))
            {
                return fIndex;
            }
        }
        return myEnd;
    }

    // Method to change a sense strand into antisense strand and print it
    private void printAntiSense(int sIndex, int fIndex, String subsequence, int length)
    {
        String newString = "";
        
        // Build antisense strand
        for (int i = 0; i < subsequence.length(); i++)
        {
            char cur = subsequence.charAt(i);

            if (cur == 'A')
            {
                newString = 'T' + newString;
            }
            else if (cur == 'T')
            {
                newString = 'A' + newString;
            }

            else if (cur == 'C')
            {
                newString = 'G' + newString;
            }
            else if (cur == 'G')
            {
                newString = 'C' + newString;
            }
        }
        
        int AntiSIndex = (length - fIndex);
        int AntiFIndex = (length - sIndex);
        System.out.println(AntiSIndex+".."+AntiFIndex+": "+newString);
    }
    
    // Main runnable file to find sense and antisense strands from a file
    public static void main(String[] args)
    {
        Gene gene = new Gene();
        gene.getGenome(gene.loadFile());
    }
}
