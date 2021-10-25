public class Gene
{
    private String sequence;

    public Gene(String senseStrand)
    {
        sequence = senseStrand;
    }

    public void getORF()
    {
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

                myEnd = setEnd(startIndex, finalIndex0, myEnd);
                myEnd = setEnd(startIndex, finalIndex1, myEnd);
                myEnd = setEnd(startIndex, finalIndex2, myEnd);

                if (myEnd != null)
                {
                    String subsequence = sequence.substring(startIndex, myEnd+3);
                    System.out.println(subsequence);
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
    }

    private Integer setEnd(int sIndex, int fIndex, Integer myEnd)
    {
        int end = sequence.length();
        if (fIndex < end && fIndex > sIndex && (fIndex-sIndex) % 3 == 0)
        {
            return Integer.valueOf(fIndex);
        }
        return myEnd;
    }

    public String toString()
    {
        return sequence;
    }
}