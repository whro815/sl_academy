package kr.co.ServiceClass.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.DAO.MemberIDao;
import kr.co.Service.Member.ILoginCheckService;

@Service
public class LoginCheckService implements ILoginCheckService
{
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public boolean loginCheck(HttpServletRequest request, HttpSession session)
	{
		MemberIDao dao = sqlSession.getMapper(MemberIDao.class);
		String userPw = request.getParameter("userPw");// 비밀번호
		
		String dbPw = dao.login(request.getParameter("userId"));  //아이디를
		if (userPw.equals(dbPw))		//select 한 passward 와 입력한 password가 일치할때
		{
			session.setAttribute("userId", request.getParameter("userId"));
			return true;
		}
		
		return false;
	}
}
