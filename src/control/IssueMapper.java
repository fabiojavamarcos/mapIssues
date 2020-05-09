package control;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import DAO.FileDAO;

public class IssueMapper {
	private String pr = "";
	private String issue = "";
	private String issueTitle = "";
	private String issueBody = "";
	private String issueComments = ""; 
	private String issueTitleLink = "";
	private String issueBodyLink = "";
	private String issueCommentsLink = ""; 
	private int isPR = 0; 
	private int isTrain = 0; 
	private String commitMessage = ""; 
	
	public IssueMapper (String file) {
		readData(file);
		
	}
	
	public void Convert1(String file) {
		// TODO Auto-generated constructor stub
	}

	private void readData(String file) {
		// TODO Auto-generated method stub
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String s = null;;
		try {
			s = br.readLine();//header!
			s = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// primeira linha do arquivo
		//String source = s;
		//System.out.println("Read: "+s);
		
		while (s != null) {
			System.out.println("linha:"+s);
			//boolean isOk = splitLine(s);
			boolean isOk = splitnew(s);
			
			if (isOk) {
				System.out.println("issue "+issue);
				System.out.println("IssueTitle "+issueTitle);
				System.out.println("IssueBody "+issueBody);
				System.out.println("IssueComments "+issueComments); 
				System.out.println("IssueTitleLink "+issueTitleLink);
				System.out.println("IssueBodyLink "+issueBodyLink);
				System.out.println("IssueCommentsLink "+issueCommentsLink); 
				System.out.println("isPR "+isPR); 
				System.out.println("isTrain "+isTrain); 
				System.out.println("CommitMessage"+commitMessage);
				
				if (issue.equals("")) {
					issue = "0";
				}
				boolean ok = insertPrIssue(pr,issue,issueTitle,issueBody,issueComments,issueTitleLink,issueBodyLink,issueCommentsLink,isPR,isTrain,commitMessage);
				if (!ok)
					System.out.println("error inserting into database : "+pr+"  - "+issue);
				else {
					System.out.println("inserterd into pr_issue : "+pr+"  issue- "+issue );
				}
			}
			try {
				s = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//generateFile();
		try {
			br.close();
			isr.close();
			is.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private boolean splitnew(String s) {
		boolean isOk = false;
		String[] values = s.split("\\s*,\\s*");
		//String[] values = s.split(",");
		try {
		pr = values[0];
		//String issueClosedDate = values[1];
		//String issueAuthor = values[2];
		issueTitle = values[1];
		issueBody = values[2];
		issueComments = values[3];
		//String prClosedDate = values [6];
		//String prAuthor = values[7];
		//String prTitle = values[8];
		//String prBody = values[9];
		String prComments = values[4];
		//String commitAuthor = values[11];
		commitMessage = values[5];
		//String commidDate = values[13];
		
		isPR = Integer.parseInt(values[6]);  
		
		String prIssue = values[7];
		issue = values[8];
		issueTitleLink = values[9];
		issueBodyLink = values[10]; 
		issueCommentsLink = values[11];
		isTrain = (int) Float.parseFloat(values[12]); 
		isOk = true;
		}
		catch(Exception e) {
			System.out.println(" line with problems:  separator missing...");
		    e.printStackTrace();
		}
		return isOk;  
		   

	}
	private boolean splitLine(String s) {
		// TODO Auto-generated method stub
		boolean isOk = false;
		String separator = ",";
		int ini = 0;
		int fim = 0;	
				
		fim = s.indexOf(separator);
		if (fim == -1) {
			System.out.println(" line with problems:   separator missing..."+fim);
			return isOk;
		}
		/*	pr issue issueTitle issueBody issueComments  issueTitleLink issueBodyLink issueCommentsLink  isPR   isTrain   commitMessage  
		 pr 
		 issue 
		 issueTitle 
		 issueBody 
		 issueComments  
		 issueTitleLink 
		 issueBodyLink 
		 issueCommentsLink  
		 isPR   
		 isTrain   
		 commitMessage  
		*/
		pr = s.substring(0, fim);
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:   separator missing..."+fim);
			return isOk;
		}
		
		String issueClosedDate = s.substring(ini+1, fim);
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String issueAuthor = s.substring(ini+1, fim);
		ini = fim;
			 
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		issueTitle = s.substring(ini+1, fim);
		ini = fim;
		
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		issueBody = s.substring(ini+1, fim);
		ini = fim;	
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		issueComments = s.substring(ini+1, fim);
		ini = fim;	
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String prClosedDate = s.substring(ini+1, fim);
		ini = fim;	
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String prAuthor = s.substring(ini+1, fim);
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String prTitle = s.substring(ini+1, fim);
		ini = fim;
			
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String prBody = s.substring(ini+1, fim);
		ini = fim;	
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String prComments = s.substring(ini+1, fim);
		ini = fim;	
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String commitAuthor = s.substring(ini+1, fim);
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String commitMessage = s.substring(ini+1, fim);
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		isPR = Integer.parseInt(s.substring(ini+1, fim));
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		String prIssue = s.substring(ini+1, fim);
		ini = fim;	 
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		issue = s.substring(ini+1, fim);
		ini = fim;	 
			
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		issueTitleLink = s.substring(ini+1, fim);
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		issueBodyLink = s.substring(ini+1, fim);
		ini = fim;
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		issueCommentsLink = s.substring(ini+1, fim);
		ini = fim;
		
		
		fim = s.indexOf(separator, ini+1);
		if (fim == -1) {
			System.out.println(" line with problems:  second separator missing..."+fim);
			return isOk;
		}
		
		isTrain = Integer.parseInt(s.substring(ini+1, fim));
		ini = fim;

		 //old = s.substring(comma1+1, s.length());
		
		//System.out.println("API: "+api+" General: "+general + " Old "+ old);
		
		isOk = true;
		
		return isOk;
		
	}

	private boolean insertPrIssue(String pr, String issue, String issueTitle2, String issueBody2, String issueComments2, String issueTitleLink2, String issueBodyLink2, String issueCommentsLink2, int isPR2, int isTrain2, String commitMessage2) {
		// TODO Auto-generated method stub
		String user = "postgres";
		String pswd = "admin"; 
		String db = "devmay2020";
		FileDAO fd = FileDAO.getInstancia(db,user,pswd);
		boolean done = fd.insertPrIssue(pr, issue, issueTitle, issueBody, issueComments,  issueTitleLink, issueBodyLink, issueCommentsLink,  isPR,   isTrain,   commitMessage  );
		if (!done) {
			System.out.println("api: "+pr+" - "+issue+"not inserted in database!!!");
		}
		return done;
	}


}
