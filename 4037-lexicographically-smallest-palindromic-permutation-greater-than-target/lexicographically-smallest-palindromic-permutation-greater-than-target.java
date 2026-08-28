class Solution {

    private String makePalindrome(String half, char middle, int n) {
        StringBuilder sb = new StringBuilder();

        sb.append(half);

        if (n % 2 == 1) {
            sb.append(middle);
        }

        sb.append(new StringBuilder(half).reverse());

        return sb.toString();
    }

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();
        int m = n / 2;

        // Count characters
        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        // Check if palindrome is possible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // Characters available for the first half
        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        String targetHalf = target.substring(0, m);

        String answer = "";

        // ------------------------------------------------
        // CASE 1:
        // targetHalf itself can be formed
        // ------------------------------------------------

        int[] temp = halfCount.clone();
        boolean possible = true;

        for (int i = 0; i < m; i++) {

            int c = targetHalf.charAt(i) - 'a';

            if (temp[c] == 0) {
                possible = false;
                break;
            }

            temp[c]--;
        }

        if (possible) {

            String candidate =
                    makePalindrome(targetHalf, middle, n);

            // STRICTLY greater
            if (candidate.compareTo(target) > 0) {
                answer = candidate;
            }
        }

        // ------------------------------------------------
        // CASE 2:
        // Find smallest half strictly greater than targetHalf
        // ------------------------------------------------

        /*
         * We try changing position i.
         *
         * Example:
         *
         * targetHalf = "abc"
         *
         * Try:
         *   ab + next bigger character
         *   a  + next bigger character
         *   next bigger character
         *
         * We scan from RIGHT to LEFT.
         */

        for (int i = m - 1; i >= 0; i--) {

            // Characters available
            int[] remaining = halfCount.clone();

            boolean prefixPossible = true;

            // Use targetHalf[0 ... i-1]
            for (int j = 0; j < i; j++) {

                int c = targetHalf.charAt(j) - 'a';

                if (remaining[c] == 0) {
                    prefixPossible = false;
                    break;
                }

                remaining[c]--;
            }

            if (!prefixPossible) {
                continue;
            }

            // At position i, choose smallest char
            // greater than targetHalf[i]
            int current = targetHalf.charAt(i) - 'a';

            int bigger = -1;

            for (int c = current + 1; c < 26; c++) {

                if (remaining[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger == -1) {
                continue;
            }

            // Put the bigger character
            remaining[bigger]--;

            StringBuilder half = new StringBuilder();

            // Same prefix
            half.append(targetHalf.substring(0, i));

            // Bigger character
            half.append((char) ('a' + bigger));

            // Fill rest with smallest characters
            for (int c = 0; c < 26; c++) {

                for (int k = 0; k < remaining[c]; k++) {
                    half.append((char) ('a' + c));
                }
            }

            String candidate =
                    makePalindrome(half.toString(), middle, n);

            if (candidate.compareTo(target) > 0) {

                if (answer.equals("") ||
                    candidate.compareTo(answer) < 0) {

                    answer = candidate;
                }
            }
        }

        return answer;
    }
}