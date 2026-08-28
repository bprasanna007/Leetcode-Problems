class Solution {
    public int lengthOfLastWord(String s) {

        int count = 0;
        int j = s.length() - 1;

        // Skip spaces at the end
        while (j >= 0 && s.charAt(j) == ' ') {
            j--;
        }

        // Count last word
        while (j >= 0 && s.charAt(j) != ' ') {
            count++;
            j--;
        }

        return count;
    }
}