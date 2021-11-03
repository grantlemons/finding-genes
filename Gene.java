import edu.duke.*;

public class Gene
{
    public void test()
    {
        getORF("ATGxxxTAG"); // Normal w/ TAG
        getORF("ATGxxxTAA"); // Normal w/ TAA
        getORF("ATGxxxTGA"); // Normal w/ TGA
        getORF("xxxATGxxxTGA"); // Leading 'x's
        getORF("xxxATGxxxTGAxxTAA"); // Extraneous End Codon
        getORF("TAG"); // Only End Codon
        getORF("ATG"); // Only Start Codon
        getORF("xx"); // No Codon
        getORF(""); // Empty string
        getORF("ATGxxTAG"); // Incorrect Spacing
        getORF("ATGxxxTAG   ATGxxxTAA"); // Two seperate genomes
        getORF("ATGxxxTAG ATGxxxTAA"); // Two seperate genomes w/ spacing not multiple of three
        getORF("xxATGxx"); // end - stop % 3 == 0
    }
    
    public String loadFile()
    {
        FileResource fr = new FileResource("sensestrand.txt"); // Open file
        String str = fr.asString();
    
        String newstr = "";
    
        // Strip any non-letters (specifically to strip white space)
        for (int i=0; i<str.length(); i++)
    
        {
            if (Character.isLetter(str.charAt(i)))
            {
    
                newstr = newstr + str.charAt(i);
            }
        }
    
        return newstr.toUpperCase(); // Also capitalize the entire string
    }

    public void getORF(String sequence)
    {
        boolean foundGenome = false;
        int pos = 0;
        while (sequence.length()-1 > pos)
        {
            int startIndex = sequence.indexOf("ATG", pos);

            if (startIndex >= 0)
            {
                //! IF ONE IS INVALID WE NEED TO CHECK AGAIN
                Integer myEnd = null;
                
                myEnd = setEnd(startIndex, "TAA", myEnd, sequence);
                myEnd = setEnd(startIndex, "TAG", myEnd, sequence);
                myEnd = setEnd(startIndex, "TGA", myEnd, sequence);

                if (myEnd != null)
                {
                    String subsequence = sequence.substring(startIndex, myEnd+3);
                    System.out.println(startIndex+1+".."+(myEnd+2+1)+": "+subsequence);
                    foundGenome = true;
                    pos = myEnd;
                }
                else
                {
                    pos = sequence.length();
                }
            }
            else
            {
                pos = sequence.length();
            }
        }
        if (!foundGenome)
        {
            System.out.println("No genomes found");
        }
    }

    private Integer setEnd(int sIndex, String searchString, Integer myEnd, String sequence)
    {
        int fIndex = sIndex;
        while (fIndex != -1 && fIndex < sequence.length()-2)
        {
            fIndex = sequence.indexOf(searchString, fIndex+1);
            if ((fIndex-sIndex) % 3 == 0 && fIndex > -1)
            {
                if (myEnd == null)
                {
                    return Integer.valueOf(fIndex);
                }
                else if (fIndex < myEnd.intValue())
                {
                    return Integer.valueOf(fIndex);
                }
            }
        }
        return myEnd;
    }
    
    public static void main(String[] args)
    {
        Gene gene = new Gene();
        gene.getORF(gene.loadFile());
    }
}