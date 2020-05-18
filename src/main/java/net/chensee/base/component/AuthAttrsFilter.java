package net.chensee.base.component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.folder.business.FolderBus;
import net.chensee.base.action.folder.service.FolderService;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.resource.vo.FolderAndColumnsVo;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.user.vo.UserDetailsAllVo;
import net.chensee.base.common.Pair;
import net.chensee.base.common.vo.DataColumnVo;
import net.chensee.base.constants.BaseConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author : xx
 * @program: base
 * @create: 2019-09-01 16:04
 * @description: 每次请求接口时 就会进入 获取资源对应的权限属性
 */
@Slf4j
public class AuthAttrsFilter extends OncePerRequestFilter {

    @Setter
    private FolderBus folderBus;

    @Setter
    private String[] ignoreMatchers;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        clearAuthRes(httpServletRequest);

        if (ignoreMatchers != null && ignoreMatchers.length > 0) {
            for (String im : ignoreMatchers) {
                if (new AntPathRequestMatcher(im).matches(httpServletRequest)) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return ;
                }
            }
        }

        Long currentResourceId = (Long)httpServletRequest.getAttribute(BaseConstants.CurrentResourceIdKey);
        if (currentResourceId != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getPrincipal() == null || ! (authentication.getPrincipal() instanceof UserDetailsAllVo)) {
                // 提示用户需要登录
                throw new MyAuthenticationException("USER_NOT_LOGIN");
            }

            UserDetailsAllVo userDetailsAllVo = (UserDetailsAllVo) authentication.getPrincipal();

            List<ResourceVo> resourceVos = userDetailsAllVo.getResourceVos();
            for (ResourceVo resourceVo : resourceVos) {
                if (currentResourceId.equals(resourceVo.getId())) {
                    httpServletRequest.setAttribute(BaseConstants.CurrentResourceKey, resourceVo);
                    calcAuthRes(httpServletRequest, resourceVo);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return ;
                }
            }
        }

        throw new MyAuthenticationException("DATA_INVALID");

    }

    private void clearAuthRes(HttpServletRequest httpServletRequest) {
        httpServletRequest.removeAttribute(BaseConstants.CurrentResourceAllColumnsKey);
        httpServletRequest.removeAttribute(BaseConstants.CurrentResourceIncludeFoldersKey);
        httpServletRequest.removeAttribute(BaseConstants.CurrentResourceExcludeColumnsKey);
    }

    private void calcAuthRes(HttpServletRequest httpServletRequest, ResourceVo resourceVo) {
        if (resourceVo == null) {
            return ;
        }
        // 获取资源所有可以看见的列
        List<DataColumnVo> columns = resourceVo.getColumns();
        if (columns != null) {
            Set<String> allColumns = new HashSet<>(columns.size());
            for (DataColumnVo dc : columns) {
                allColumns.add(dc.getKey());
            }
            if (allColumns.size() > 0) {
                httpServletRequest.setAttribute(BaseConstants.CurrentResourceAllColumnsKey, allColumns);
            }
        }

        List<FolderAndColumnsVo> folderAndColumns = resourceVo.getFolderAndColumns();
        if (folderAndColumns != null) {
            Map<Long, Set<Long>> allFolderAndLeafs = getAllFolderAndLeafs(httpServletRequest);
            // 存放资源所有能看见的文件夹
            Set<Long> includeFolders = new HashSet<>(folderAndColumns.size());
            // 存放资源所有排除的列
            Map<Long, Set<String>> exs = new HashMap<>(folderAndColumns.size());
            for (FolderAndColumnsVo fac : folderAndColumns) {
                if (fac.getFolder() != null) {
                    // 将所有的folder转换为leaf
                    Set<Long> leafs = allFolderAndLeafs.get(fac.getFolder());
                    if (leafs != null && leafs.size() != 0) {
                        includeFolders.addAll(leafs);
                    }
                    // 合并列
                    if (fac.getExcludeColumns() != null && fac.getExcludeColumns().size() > 0) {
                        if (fac.getIncludeColumns() != null && fac.getIncludeColumns().size() > 0) {
                            Set<String> newResult = new HashSet<>();
                            newResult.addAll(fac.getExcludeColumns());
                            for (String inc : fac.getIncludeColumns()) {
                                if (fac.getExcludeColumns().contains(inc)) {
                                    newResult.remove(inc);
                                }
                            }
                        } else {
                            exs.put(fac.getFolder(), fac.getExcludeColumns());
                        }
                    }
                }
            }
            if (includeFolders.size() > 0) {
                httpServletRequest.setAttribute(BaseConstants.CurrentResourceIncludeFoldersKey, includeFolders);
            }
            if (exs.size() > 0) {
                httpServletRequest.setAttribute(BaseConstants.CurrentResourceExcludeColumnsKey, exs);
            }
        }
    }

    private Map<Long, Set<Long>> getAllFolderAndLeafs(HttpServletRequest httpServletRequest) {
        List<FolderVo> allFolder = folderBus.getAllFolder();
        if (allFolder != null) {
            Map<Long, Set<Long>> childrenForFolder = new HashMap<>();
            Map<Long, FolderVo> folderVoMap = new HashMap<>();
            for (FolderVo folderVo : allFolder) {
                Long id = folderVo.getId();
                folderVoMap.put(id, folderVo);
                Long parentId = folderVo.getParentId();

                if (folderVo.getIsLeaf() == 0) {
                    childrenForFolder.put(id, new HashSet<>());
                }

                if (parentId > 0) {
                    Set<Long> longs = childrenForFolder.get(parentId);
                    if (longs == null) {
                        longs = new HashSet<>();
                        childrenForFolder.put(parentId, longs);
                    }
                    longs.add(id);
                }
            }

            httpServletRequest.setAttribute(BaseConstants.CurrentResourceFoldersKey, folderVoMap);

            for (Long lf : childrenForFolder.keySet()) {
                Set<Long> longs = childrenForFolder.get(lf);
                if (longs != null && longs.size() > 0) {
                    Set<Long> allLeafs = new HashSet<>();
                    for (Long slf : longs) {
                        getLeafFolder(slf, folderVoMap, childrenForFolder, allLeafs);
                    }
                    childrenForFolder.put(lf, allLeafs);
                }
            }

            httpServletRequest.setAttribute(BaseConstants.CurrentResourceFolderAndLeafsKey, childrenForFolder);
            return childrenForFolder;
        }
        return null;
    }

    private void getLeafFolder(Long slf, Map<Long, FolderVo> folderVoMap, Map<Long, Set<Long>> childrenForFolder, Set<Long> allLeafs) {
        if (folderVoMap.get(slf).getIsLeaf() == 1) {
            allLeafs.add(slf);
        } else {
            Set<Long> longs = childrenForFolder.get(slf);
            if (longs != null) {
                for (Long tmp : longs) {
                    getLeafFolder(tmp, folderVoMap, childrenForFolder, allLeafs);
                }
            }
        }
    }

}