package edu.carleton.comp4601.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.codehaus.plexus.util.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.carleton.comp4601.model.Pages;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Matcher;

public class MyCrawler extends WebCrawler {

	static Set<Integer> set = new HashSet<Integer>();
	static Set<Integer> setM = new HashSet<Integer>();
	static Set<Integer> setJ = new HashSet<Integer>();

	static Set<String> urls = new HashSet<String>();

	static Set<String> redirects = new HashSet<String>();

	boolean repeat = false;
	static int count = 0;

	long startCrawlTime = 0;
	long endCrawlTime   = 0;

	static int htmlcount = 0;
	static int jobCount = 0;

	static int jobboomcount = 0;
	static int visitjobbomcount = 0;


	String searchWord = CrawlerController.crawlSearchWord;

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp4|zip|gz))$");

   //turns testing prints on/off
   boolean detail = false;
    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
    	boolean monsterJob = false;
    	boolean workopolisJob = false;
    	boolean jobboomJob = false;

    	boolean repeat = false;

    	//System.out.println("should: " + url);
    	 int pagenumber;
         String href = url.getURL().toLowerCase();



        	 /*Monster*/
        	 if (href.startsWith("https://www.monster.ca/jobs/search/?q=" + searchWord + "&where=canada&page=")) {
        		 //Set Page number
        		 String pgnum = href.substring(href.indexOf("page=")+5);
        		 pagenumber = Integer.valueOf(pgnum);

        		//Ensure pagenumber is unque
             	if(setM.contains(pagenumber)){
             		repeat = true;
             	} else{
             		repeat = false;
             		setM.add(pagenumber);
             	}

        	 }

             /* Jobboom */
        	 if((href.startsWith("https://www.jobboom.com/en/job/" + searchWord + "_canada/_k-"))){


        		 //Set Page Number
        		 if(href.indexOf("?")!=-1){pagenumber =  Integer.parseInt(href.substring((href.indexOf("k-")+2),  href.indexOf("?")));}
        		 else{pagenumber =  Integer.parseInt(href.substring((href.indexOf("k-")+2))); }

        		 //Debug print
        		 if(detail==true){System.out.println("Job boom HIT: " + pagenumber);}

        		//Ensure Search Page is unique
             	if(setJ.contains(pagenumber)){
             		repeat = true;
             	} else{
             		repeat = false;
             		setJ.add(pagenumber);
             	}

        	 }


        	 //********************JOB POSTINGS*****************************//
        	 //JOBBOOM
        	 if (href.contains("/en/job-description/")){

        		 //Ensure job posting is unique
        		 if(!(urls.contains(href))){
        			 urls.add(href);

        			 jobboomJob = true;
        		 }
        	 }

        	 //MONSTER
        	 if (href.contains("https://job-openings.monster.ca/")){

        		 //Ensure job posting is unique
        		  if(!(urls.contains(href))){
        			 urls.add(href);

        			 monsterJob = true;
        		 }
        	 }


        	 	//Is webpage a search result page?
        	 	boolean pageBool =
                		href.startsWith("https://www.monster.ca/jobs/search/?q=" + searchWord + "&where=canada&page=")
         				|| href.startsWith("https://www.jobboom.com/en/job/" + searchWord + "_canada/_k-");


        	 	//is it a webpage OR a unique job post page?
        	 	boolean shouldvisit = !repeat &&
         	    	(pageBool ||
                    ((monsterJob==true) || (workopolisJob==true) || (jobboomJob==true)));


         return !FILTERS.matcher(href).matches() && shouldvisit;
     }



     @Override
     public boolean shouldFollowLinksIn(WebURL url){
    	 startCrawlTime = System.currentTimeMillis();
    	 return true;
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
    	 endCrawlTime = (System.currentTimeMillis() - startCrawlTime) * 10;
    	 this.getMyController().getConfig().setPolitenessDelay(new Long(endCrawlTime).intValue());

         String url = page.getWebURL().getURL();
        if(detail) {System.out.println("- - - - - - - - - - -  - - - - - - - - \n Search page URL: " + url + "\n - - - - - - - - - - -  - - - - - - - -");}

         //Step ZERO: Basic Parsing...
         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();
             Document document = Jsoup.parse(htmlParseData.getHtml());
             String expression = document.html().toString().replace("\n", "");




             /*Monster*/
             if(url.startsWith("https://job-openings.monster.ca")){

	    	 	//Pre-Parse
	        	String linkOfJobPost = url;
	        	urls.add(linkOfJobPost);

	        	Pages pages = Pages.getInstance();
	        	pages.addPage(linkOfJobPost, "");

	     		//Parsing
	     		 //--setup
	     		count++;
	     		HashMap<String, Boolean> skills = pages.getSkills(linkOfJobPost);

	     		String jobExpression = html;
	     		for(String skill: skills.keySet()){

	     			//These are the only natural language occurences we care about
	     			if((jobExpression.toLowerCase().indexOf(" " + skill + " ")  != -1) ||
	     					(jobExpression.toLowerCase().indexOf(" " + skill + ", ") != -1) ||
	     					(jobExpression.toLowerCase().indexOf(" " + skill + ". ") != -1) ||
	     					(jobExpression.toLowerCase().indexOf(" " + skill + "\n") != -1)){

	     				Pages.getInstance().addSkill(linkOfJobPost, skill);
	     				if(detail==true){System.out.println("we gotta skill boy: " + skill);}


	     			}

	     		}



             }

             /* Jobboom */
             if(url.startsWith("https://www.jobboom.com/en/job-description/")){
            	String linkOfJobPost = url;
            	String jobPage = html;

         		//Pre-Parse
         		Pages pages = Pages.getInstance();
         		pages.addPage(linkOfJobPost, "");

         		//Step Three: Parsing
         		 //--setup
         		count++;
         		HashMap<String, Boolean> skills = pages.getSkills(linkOfJobPost);

         		String jobExpression = jobPage.replace("\r", " ").replace("\n", " ").replace(",", " ").replace(".", " ");
         		//--look for each skill in page and make a note if it exists
         		for(String skill: skills.keySet()){
         			if(jobExpression.toLowerCase().indexOf(" " + skill + " ") != -1){
         				if(((skill.equals("html")||skill.equals("javascript")))&&(StringUtils.countMatches(jobExpression.toLowerCase(), " " + skill + " ")>=2)) {
         					if(detail==true){System.out.println("count: "+StringUtils.countMatches(jobExpression.toLowerCase(), " " + skill + " "));}
         					if(detail==true){System.out.println("we gotta skill boy: " + skill);}
         					Pages.getInstance().addSkill(linkOfJobPost, skill);
         				} else if (((skill.equals("node")||skill.equals("css")))&&(StringUtils.countMatches(jobExpression.toLowerCase(), " " + skill + " ")>=3)) {
         					if(detail==true){System.out.println("count: "+StringUtils.countMatches(jobExpression.toLowerCase(), " " + skill + " "));}
         					if(detail==true){System.out.println("we gotta skill boy: " + skill);}
         					Pages.getInstance().addSkill(linkOfJobPost, skill);
         				} else if (!(skill.equals("html"))&&!(skill.equals("css"))&&!(skill.equals("javascript"))&&!(skill.equals("node"))) {
         					if(detail==true){System.out.println("we gotta skill boy: " + skill);}
         					Pages.getInstance().addSkill(linkOfJobPost, skill);
         				}
         			}
         			if(skill.equals(searchWord)) { //Always add the searchWord for every page
         				Pages.getInstance().addSkill(linkOfJobPost, skill);
         			}
         		}



             }

         	if(detail==true){System.out.println("urls set size: " + urls.size());}

         }
     }




}
