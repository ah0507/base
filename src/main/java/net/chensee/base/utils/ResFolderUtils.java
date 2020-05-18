package net.chensee.base.utils;

import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.constants.BaseConstants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author xx
 * @program base
 * @date 2019-10-14 16:19
 * @description
 */
public class ResFolderUtils {
    /**
     * 获取当前请求所有可视文件夹
     * @return
     */
    public static Set<Long> getCurrentFolder() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return (Set<Long>) request.getAttribute(BaseConstants.CurrentResourceIncludeFoldersKey);
        }
        return null;
    }

    /**
     * 获取所有的非leaf文件夹，以及其所对应得leaf文件夹
     * @return
     */
    public static Map<Long, Set<Long>> getAllFolderAndLeafs() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return (Map<Long, Set<Long>>) request.getAttribute(BaseConstants.CurrentResourceFolderAndLeafsKey);
        }
        return null;
    }

    /**
     * 获取所有的文件夹
     * @return
     */
    public static Map<Long, FolderVo> getAllFolders() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return (Map<Long, FolderVo>) request.getAttribute(BaseConstants.CurrentResourceFoldersKey);
        }
        return null;
    }
}
