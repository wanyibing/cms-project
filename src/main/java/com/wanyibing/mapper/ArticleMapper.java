package com.wanyibing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wanyibing.entity.Article;
import com.wanyibing.entity.Category;
import com.wanyibing.entity.Channel;
import com.wanyibing.entity.Comment;
import com.wanyibing.entity.Complain;

public interface ArticleMapper {

	
	List<Article> listByUser(@Param("id")Integer id);

	@Delete("UPDATE cms_article SET deleted=#{status} WHERE id=#{id}")
	int updateStatus(@Param("id")int id, @Param("status")int status);

	@Select("select id,name from cms_channel")
	List<Channel> getChannels();

	@Select("select id,name from cms_category WHERE channel_id=#{cid}")
	List<Category> getcategoris(int cid);

	@Insert("INSERT INTO cms_article(title,content,picture,channel_id,category_id,user_id,hits,hot,status,deleted,created,updated,commentCnt,articleType)"
			+ " VALUES(#{title},#{content},#{picture},#{channelId},#{categoryId},#{userId},0,0,0,0,now(),now(),0,#{articleType})")
	int add(Article article);

	@Update("UPDATE cms_article SET title=#{title},content=#{content},picture=#{picture},channel_id=#{channelId},"
			+ " category_id=#{categoryId},status=0,"
			+ "updated=now() WHERE id=#{id} ")
	int update(Article article);

	Article getById(int id);

	List<Article> list(@Param("status")String status);

	@Select("SELECT id,title,channel_id channelId , category_id categoryId,status ,hot "
			+ " FROM cms_article WHERE id = #{value} ")
	Article getInfoById(int id);

	@Update("UPDATE cms_article SET status=#{myStatus} WHERE id=#{myid}")
	int setCheckStatus(@Param("myid") int id, @Param("myStatus") int status);

	@Update("UPDATE cms_article SET hot=#{hot} WHERE id=#{myid}")
	int setHot(@Param("myid") int id, @Param("hot") int status);

	@Update("UPDATE cms_article SET deleted=1 where id=#{id}")
	int deletearticle(int id);

	List<Article>  hotList();

	List<Article> lastList(int pageSize);

	@Select("SELECT id,name FROM cms_category where channel_id=#{value}")
	List<Category> getCategoriesByChannelId(int channelId);

	@Insert("INSERT INTO cms_comment(articleId,userId,content,created)"
			+ " VALUES(#{articleId},#{userId},#{content},NOW())")
	int addComment(Comment comment);

	@Update("UPDATE cms_article SET commentCnt=commentCnt+1 WHERE id=#{value}")
	int increaseCommentCnt(int articleId);

	//查询评论
	@Select("SELECT u.url as url,c.id,c.articleId,c.userId,u.username as userName,c.content,c.created FROM cms_comment as c "
			+ " LEFT JOIN cms_user as u ON u.id=c.userId "
			+ " WHERE articleId=#{value} ORDER BY c.created DESC")
	List<Comment> getComments(int id);

	List<Article> getArticles(@Param("channelId")  int channleId, @Param("catId") int catId);

	@Insert("INSERT INTO cms_complain(article_id,user_id,complain_type,"
			+ "compain_option,src_url,picture,content,email,mobile,created)"
			+ " VALUES(#{articleId},#{userId},"
			+ "#{complainType},#{compainOption},#{srcUrl},#{picture},#{content},#{email},#{mobile},now())")
	int addCoplain(Complain complain);

	
	@Update("UPDATE cms_article SET complainCnt=complainCnt+1,status=if(complainCnt>10,2,status) "
			+ " WHERE id=#{value}")
	void increaseComplainCnt(Integer articleId);

	List<Complain> getComplains(int articleId);

	
	List<Complain> complainlist();


	
	
}
