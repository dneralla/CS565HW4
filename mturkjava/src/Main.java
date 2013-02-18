public class Main {

	SiteCategory siteCategory;
	Reviewer reviewer;
	SimpleSurvey survey;
	ParseWord parseword;

	public Main() {
		siteCategory = new SiteCategory();
		reviewer = new Reviewer();
		survey = new SimpleSurvey();
		parseword = new ParseWord();
	}

	public static void main(String args[]) throws Exception {

		/* For Posting initial Set of Hits */
		Main main = new Main();
		if (main.siteCategory.hasEnoughFund())
			main.siteCategory.createSiteCategoryHITs();

		/* Run the reviewer */
		main.reviewer.printResults("site_category.input.success", "output.txt");

		/* Getting words from output file and writing in to separate files */
		main.parseword.setFilePaths("output.txt", "keywordsAll.txt",
				"keywordsUnique.txt");
		main.parseword.getAllWords();
		main.parseword.getUniqueWords();

		/* For Posting next set of HITs for filtering */
		if (main.survey.hasEnoughFund())
			main.survey.createSimpleSurvey();
		/* results from second Hit */
		main.reviewer.printResults("survey.success", "output2.txt");
		main.parseword.setFilePaths("output2.txt", "keywordsAll2.txt",
				"keywordsUnique2.txt");
		/* purify Words in to purifirs text file */
		PurifyWordFromBad.getPurifiedWords();
		/* For generating WordCloud */
		wordCloud.readPurified();

	}

}
