public class Gene
{    
    public void test()
    {
        String[] testCases = new String[12];
        testCases[0] = "ATGxxxTAG"; // Normal w/ TAG
        testCases[1] = "ATGxxxTAA"; // Normal w/ TAA
        testCases[2] = "ATGxxxTGA"; // Normal w/ TGA
        testCases[3] = "xxxATGxxxTGA"; // Leading 'x's
        testCases[4] = "xxxATGxxxTGAxxTAA"; // Extraneous End Codon
        testCases[5] = "TAG"; // Only End Codon
        testCases[6] = "ATG"; // Only Start Codon
        testCases[7] = "xx"; // No Codon
        testCases[8] = ""; // Empty string
        testCases[9] = "ATGxxTAG"; // Incorrect Spacing
        testCases[10] = "ATGxxxTAG   ATGxxxTAA"; // Two seperate genomes
        testCases[11] = "ATGxxxTAG ATGxxxTAA"; // Two seperate genomes w/ spacing not multiple of three
        
        Gene gene = new Gene();
        
        for (int i=0; i < testCases.length; i++)
        {
            System.out.println("Test Case "+ i + " ----------- \""+ testCases[i]+"\"");
            gene.getORF(testCases[i]);
        }
    }

    public void getORF(String sequence)
    {
        boolean foundORF = false;
        int pos = 0;
        while (sequence.length()-1 > pos)
        {
            int startIndex = sequence.indexOf("ATG", pos);

            if (startIndex >= 0)
            {
                int finalIndex0 = sequence.indexOf("TAA", startIndex);
                int finalIndex1 = sequence.indexOf("TAG", startIndex);
                int finalIndex2 = sequence.indexOf("TGA", startIndex);
                Integer myEnd = null;
                
                myEnd = setEnd(startIndex, finalIndex0, myEnd, sequence);
                myEnd = setEnd(startIndex, finalIndex1, myEnd, sequence);
                myEnd = setEnd(startIndex, finalIndex2, myEnd, sequence);

                if (myEnd != null)
                {
                    String subsequence = sequence.substring(startIndex, myEnd+3);
                    System.out.println(startIndex+".."+myEnd+": "+subsequence);
                    foundORF = true;
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
        if (!foundORF)
        {
            System.out.println("No genomes found");
        }
    }

    private Integer setEnd(int sIndex, int fIndex, Integer myEnd, String sequence)
    {
        if (myEnd == null)
        {
            if ((fIndex-sIndex) % 3 == 0)
            {
                return Integer.valueOf(fIndex);
            }
        }
        else
        {
            if ( fIndex < myEnd.intValue() && (fIndex-sIndex) % 3 == 0)
            {
                return Integer.valueOf(fIndex);
            }
        }
        return myEnd;
    }
}
