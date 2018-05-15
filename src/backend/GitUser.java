package backend;

public class GitUser 
{
	public String user;
	public String gitUser;
	public String gitPic;
	public String lastAT;
	public String hash;
	public String password;
	public GitUser(String user,String gitUser,String password, String gitPic, String lastAT, String hash) {
		super();
		this.password=password;
		this.user=user;
		this.gitUser = gitUser;
		this.gitPic = gitPic;
		this.lastAT = lastAT;
		this.hash = hash;
	}
	
}
