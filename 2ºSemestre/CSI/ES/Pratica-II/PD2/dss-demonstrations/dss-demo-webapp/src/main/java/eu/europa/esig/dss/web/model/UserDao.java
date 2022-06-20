package eu.europa.esig.dss.web.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;    
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;    

public class UserDao {
    @Autowired
    private PasswordEncoder passwordEncoder;

    JdbcTemplate template;
    
    public void setTemplate(JdbcTemplate template) {    
        this.template = template;    
    }    

    private User findUserByUsername(String username){
        String sql="select * from accounts where username=?";
        try{
            User u = template.queryForObject(
                sql, 
                new Object[]{username},
                new BeanPropertyRowMapper<User>(User.class));
                return u;
        }catch(DataAccessException e){
            return null;
        }
    }

    public int save(User u){
        if (findUserByUsername(u.getUsername()) != null)
            return 0;
        
        String password = passwordEncoder.encode(u.getPassword());
        String phoneNumber = ""; // para nao ser null e ter problemas a apresentar o form da area do utilizador
        String sql="insert into accounts(username,password,phoneNumber) values('"+u.getUsername()+"','"+password+"','"+phoneNumber+"')";    
        return template.update(sql);    
    }

    public String getUserPhoneNumber(String username){
        User u = findUserByUsername(username);
        if (u == null)
            return null;
        
        return u.getPhoneNumber();
    }

    public int updatePhoneNumber(String username, String phoneNumber){
        /* é necessário que a condição seja pela primary_key (ou seja, pelo id)
        por isso vamos obtê-lo primeiro*/
        User u = findUserByUsername(username);
        if (u == null)
            return 0;
        int id = u.getId();

        String sql="update accounts set phoneNumber='"+phoneNumber+"' where id="+id;
        return template.update(sql);
    }
}
