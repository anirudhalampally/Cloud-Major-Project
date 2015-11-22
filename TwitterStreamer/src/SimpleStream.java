import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONTokener;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class SimpleStream {
    public static void main(String[] args) throws IOException, TwitterException {
    	
    	final BufferedWriter bw = new BufferedWriter(new FileWriter(new File("tweets.txt")));
    	final BufferedWriter bw1 = new BufferedWriter(new FileWriter(new File("hashtags.txt")));
    	System.setProperty("http.proxyHost", "proxy.iiit.ac.in");
        System.setProperty("http.proxyPort", "8080");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("RSOSpgmeFKB1QPRlXCc8EDzxU");
        cb.setOAuthConsumerSecret("xSyByo1hRAoMVOZmRlgagHdZ1JtCyxk3YsIFeH4XO0i4bhy4FV");
        cb.setOAuthAccessToken("137933671-i8oX1r9yn6c3DtEvn0SL05lcyfHITKXF6y6qYUd9");
        cb.setOAuthAccessTokenSecret("hel5dr5VL8IMsJjKUv2Sd8QM973cUZOJXhGy830pxG7LB");
        
       // TwitterFactory tf = new TwitterFactory(cb.build());
       // Twitter twitter = tf.getInstance();
        //Twitter twitter = TwitterFactory.getSingleton();
        /*List<Status> statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline.");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                               status.getText());
        }*
        
        Query query = new Query("ireland");
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }*/
        
       
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
            	String txt="";
            	String txt1="";
            	HashtagEntity[] entity =status.getHashtagEntities();
                for(int i=0;i<entity.length;i++){
                	txt=txt+"#"+entity[i].getText()+" ";
                	txt1=txt1+"#"+entity[i].getText()+" ";
                }
                String content = status.getText();
                txt=txt+content;
                System.out.println(txt+"\n");
            	try {
            		//status.get
            		bw1.write(txt1+"\n");
					bw.write(txt+"\n");
					bw.flush();
					bw1.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
                User user = status.getUser();
                // gets Username
                String username = status.getUser().getScreenName();
                //System.out.println(username);
                String profileLocation = user.getLocation();
                //System.out.println(profileLocation);
                long tweetId = status.getId(); 
                //System.out.println(tweetId);
                //System.out.println("Content: "+content +"\n");

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

        };
        FilterQuery fq = new FilterQuery();
    
        String keywords[] = {"ndtv","bbc","foxnews","cnn","cnbc"};

        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);  

    }
    

    /**
     *  Processes a stream of tweets and writes them to a file one tweet per line. Each tweet here is represented by a JSON document.
     * @param is input stream already connected to the streaming API
     * @param outFilePath file to put the collected tweets in
     * @throws InterruptedException
     * @throws IOException
     */
    public void ProcessTwitterStream(InputStream is, String outFilePath)
    {
        BufferedWriter bwrite = null;
        try {
            JSONTokener jsonTokener = new JSONTokener(new FileReader("tweets.txt"));
            ArrayList<JSONObject> rawtweets = new ArrayList<JSONObject>();
            int nooftweetsuploaded = 0;
            while (true) {
                try {                    
                    JSONObject temp = new JSONObject(jsonTokener);                    
                    rawtweets.add(temp);
//                    System.out.println(temp);
                    if (rawtweets.size() >= 10)
                    {
                        Calendar cal = Calendar.getInstance();
                        String filename = outFilePath + "tweets_" + cal.getTimeInMillis() + ".json";
                        bwrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
                        nooftweetsuploaded += 10;
                        //Write the collected tweets to a file
                        for (JSONObject jobj : rawtweets) {
                            bwrite.write(jobj.toString());
                            bwrite.newLine();
                        }
                        System.out.println("Written "+nooftweetsuploaded+" records so far");
                        bwrite.close();
                        rawtweets.clear();
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
}