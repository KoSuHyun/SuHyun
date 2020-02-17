package spring;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberRegisterService {
	private MemberDao memberDao;
	public MemberRegisterService(MemberDao memberDao){
		this.memberDao = memberDao;
	}
	
	private PasswordEncoder encoder;
	public void setPasswordEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}
	
	
	public void regist(RegisterRequest req){
		Member member = memberDao.selectByEmail(req.getEmail());	//이메일 확인
		if(member != null){
			throw new AlreadyExistingMemberException("dup email " + req.getEmail());
		}
		
//		//암호화 시킬 시점(해싱 시킬 시점)
//		StringBuffer encryptPassword = new StringBuffer();
//		
//		String password = req.getPassword();
//		String salt = Sha256Util.genSalt();	//솔트값 만들어 주는 애
//		
//		encryptPassword.append(Sha256Util.getEncrypt(password, salt));
//		encryptPassword.append("\\$").append(salt);
//		
//		System.out.println(encryptPassword.toString());
//		
//		req.setPassword(encryptPassword.toString());	//spring 사용 안하고 비번 바꾸는 개념?
		
		String password = req.getPassword();
		password = encoder.encode(password);	//spring 기능 사용) 해싱돼서 패스워드 만들어짐
		req.setPassword(password);
		
		Member newMember = new Member(
				req.getEmail(),
				req.getPassword(),
				req.getName(),
				new Date()
				);
		memberDao.insert(newMember);		
	}
}
