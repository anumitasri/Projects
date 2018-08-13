import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Twitterapp {

	public static void main(String[] args) throws TwitterException {
		// TODO Auto-generated method stub
		/*
		 * ConfigurationBuilder cb = new ConfigurationBuilder();
		 * cb.setDebugEnabled(true)
		 * .setOAuthConsumerKey("G7bxtz06WnRz9ICynlYzbT1Ae")
		 * .setOAuthConsumerSecret(
		 * "ePo6NJFefUVzMRLFWM9h0FOQc1Bap0iocIsKqSEyMtGDywRX0D")
		 * .setOAuthAccessToken(
		 * "2773041256-KgL2uUeX6eTY40ozzpUtsFksMBFOudscjuXvWnK")
		 * .setOAuthAccessTokenSecret(
		 * "muZaZVGto5hNqlBhBHTq6q5DuPhJWSwg22w232j2z5GZq"); TwitterFactory tf =
		 * new TwitterFactory(cb.build()); Twitter twitter = tf.getInstance();
		 */

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("Lka1z7W9ugWeDPWiVQ95PnV5v")
				.setOAuthConsumerSecret(
						"ziR51Of7PABImYORbxgVLVWzyOI9MdWHWHMEDQkfTlFQ5MzXkD")
				.setOAuthAccessToken(
						"1008294866-yGtZHhjM1KnnyihbMP5DOfULVx95aSc8Yo6uUs6")
				.setOAuthAccessTokenSecret(
						"Zg98Dbf3HygFoVJfNjRfSc3H93iFcCZABMRFyNPE6INOL");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		// Twitter twitter = TwitterFactory.getSingleton();
		/*
		 * Query query = new Query("#job"); QueryResult result =
		 * twitter.search(query); for (Status status : result.getTweets()) {
		 * System.out.println("@" + status.getUser().getScreenName() + ":" +
		 * status.getText()); }
		 */
		String hashtag1 = "#job";
		Query query = new Query(hashtag1);
		GeoLocation geo = new GeoLocation(39.858648, -99.69866);
		query.setGeoCode(geo, 3000, Unit.mi);
		int numberOfTweets = 20;
		long lastID = Long.MAX_VALUE;
		ArrayList<Status> tweets = new ArrayList<Status>();
		while (tweets.size() < numberOfTweets) {
			if (numberOfTweets - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(numberOfTweets - tweets.size());
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				println("Gathered " + tweets.size() + " tweets");
				for (Status t : tweets)
					if (t.getId() < lastID)
						lastID = t.getId();

			}

			catch (TwitterException te) {
				println("Couldn't connect: " + te);
			}
			;
			query.setMaxId(lastID - 1);
		}

		ArrayList<JobVo> listJobs = new ArrayList<JobVo>();

		for (int i = 0; i < tweets.size(); i++) {

			JobVo job = new JobVo();
			job.setHashtag(hashtag1);
			Status t = (Status) tweets.get(i);

			GeoLocation loc = t.getGeoLocation();

			String user = t.getUser().getScreenName();
			job.setTwitterUser(user);
			String msg = t.getText();

			String time = "";
			if (loc != null) {
				Double lat = t.getGeoLocation().getLatitude();
				Double lon = t.getGeoLocation().getLongitude();
				println(i + " USER: " + user + " wrote: " + msg
						+ " located at " + lat + ", " + lon);
			} else
				println(i + " USER: " + user + " wrote: " + msg);

			int ini = msg.indexOf("https://");
			String url = "";
			if (ini != -1) {
				String linkk = msg.substring(ini);
				int fin = linkk.indexOf(" ");

				if (fin != -1) {
					url = linkk.substring(0, fin);

					System.out.println("LINK " + url);
					job.setTwiterLink(url);
				} else {
					url = linkk.substring(0);
					if (url.contains(")"))
						url = url.replaceAll(")", "");
					if (url.contains("_"))
						url = url.replaceAll("_", "");

					System.out.println("LINK " + url);
					job.setTwiterLink(url);
				}
			}

			if (!url.equalsIgnoreCase("")) {

				Document doc;
				try {
					doc = Jsoup.connect(url).get();

					if (doc != null) {

						System.out.println("TITLE " + doc.title());
						job.setTitle(doc.title());
						Elements desc = doc.select("[itemprop=description]");
						if (desc.size() != 0) {
							for (Element d : desc) {
								System.out.println(" DESC " + d.text());
								job.setDescription(d.text());
							}

						} else {
							//Element bo = doc.body();
							Elements ul = doc.select("ul");
							String stDescList = "";
							if (ul.size() != 0) {

								Elements li = ul.select("li");
								for (Element l : li) {
									if (l.text().contains("Facebook")
											|| l.text()
													.contains("Terms of Use")
											|| l.text().contains("Google+")
											|| l.text().contains("About Us")
											|| l.text().contains("Twitter")
											|| l.text().contains("LinkedIn")
											|| l.text().contains("Contact Us")
											|| l.text().contains("Job Search")
											|| l.text().contains("Contacts")
											|| l.text().contains("Home")
											|| l.text().contains("Privacy")
											|| l.text().contains("Sign Out")
											|| l.text().contains("United States")) {
										break;
									} else {
										//if (l.text().contains(".")) {
											System.out.println(" li "
													+ l.text());
											stDescList = stDescList + l.text()
													+ "|";
										//}
									}

								}
							} else {
								Elements ol = doc.select("ol");
								if (ol.size() != 0) {

									Elements li = ol.select("li");
									for (Element l : li) {
										if (l.text().contains("Facebook")
												|| l.text().contains(
														"Terms of Use")
												|| l.text().contains("Google+")
												|| l.text()
														.contains("About Us")
												|| l.text().contains("Twitter")
												|| l.text()
														.contains("LinkedIn")
												|| l.text().contains(
														"Contact Us")
												|| l.text().contains(
														"Job Search")
												|| l.text()
														.contains("Contacts")
												|| l.text().contains("Home")) {
											break;
										} else {
											//if (l.text().contains(".")) {
												System.out.println(" li "
														+ l.text());

												stDescList = stDescList
														+ l.text() + "|";
											//}
										}
									}
								}
							}

							job.setDescription(stDescList);

						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}

			listJobs.add(job);

		}

		for (JobVo j : listJobs) {
			System.out.println("TITLE " + j.getTitle() + " DESC "
					+ j.getDescription() + " TWITTER " + j.getTwitterUser()
					+ " LINK " + j.getTwiterLink());
		/*	try {
				String[] sent = getSentences(j.getDescription());
				// String[] sentences=getSentences(doc.body().toString());
				if (sent != null)
					for (String st : sent) {
						System.out.println(" NEW " + st);
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}

		DataManager dm = new DataManager();
		dm.insertData(listJobs);

		dm.upDate();

	}

	private static void println(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}

	private static String[] getSentences(String st)
			throws FileNotFoundException {

		InputStream modelIn = new FileInputStream("en-sent.bin");
		String[] sentences = null;

		try {
			SentenceModel model = new SentenceModel(modelIn);
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
			sentences = sentenceDetector.sentDetect(st);
			return sentences;

		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		} finally {
			if (modelIn != null) {
				try {

					modelIn.close();
					return sentences;

				} catch (IOException e) {
				}
			}
		}
	}

}
