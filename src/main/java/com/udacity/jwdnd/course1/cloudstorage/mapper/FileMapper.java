package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    List<String> getAllFileNames(Integer userId);

    @Results(id="fileMap", value={
            @Result(column="fileid", property="fileId"),
            @Result(column="filename", property="fileName"),
            @Result(column="contenttype", property="contentType"),
            @Result(column="filesize", property="fileSize"),
            @Result(column="filedata", property="fileData", jdbcType = JdbcType.BLOB),
            @Result(column="userid", property="userId")
    })
    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    File getFile(String fileName, Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    int deleteFile(String fileName, Integer userId);
}
