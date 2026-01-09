package com.clt.matlink.common.file.config;

import com.clt.matlink.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 服务相关配置
 * 
 * @author zm
 */
@Component
@ConfigurationProperties(prefix = "myproject")
@Data
public class FileServerConfig
{

    /** 项目名称 */
    private String name;

    /** 上传路径 */
    private String uploadPath;
    /** 上传文件url前缀 */
    private String fileUrlPrefix;

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     * 
     * @return 服务地址
     */
    public String getDomain()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}
