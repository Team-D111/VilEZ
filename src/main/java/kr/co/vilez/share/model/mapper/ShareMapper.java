package kr.co.vilez.share.model.mapper;

import kr.co.vilez.share.model.dto.ImgPath;
import kr.co.vilez.share.model.dto.ShareDto;
import kr.co.vilez.share.model.dto.ShareListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ShareMapper {
    List<ShareListDto> loadMyShareList(int userId) throws SQLException;
    List<ShareListDto> loadShareList() throws SQLException;
    void delete(int boardId) throws SQLException;
    void update(ShareDto shareDto) throws SQLException;
    ShareDto detailArticle(String boardId) throws SQLException;
    int insert(ShareDto shareDto) throws SQLException;
//    List<ShareListDto> getList() throws SQLException;
}
