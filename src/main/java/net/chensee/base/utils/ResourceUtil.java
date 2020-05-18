package net.chensee.base.utils;

import net.chensee.base.action.resource.vo.FolderAndColumnsVo;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.role.po.RoleResourcePo;
import net.chensee.base.action.user.po.UserResourcePo;
import net.chensee.base.action.userGroup.po.UserGroupResourcePo;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * 资源处理工具类
 *
 * @author xx
 */
public class ResourceUtil {

    /**
     * 合并
     * @param resourceVos
     * @return
     */
    public static List<ResourceVo> mergeResourceVo(List<ResourceVo> resourceVos) {
        // 1.相同的资源先进行合并
        Map<Long, List<ResourceVo>> resourceVoMap = new HashMap<>();
        for (ResourceVo vo : resourceVos) {
            if (resourceVoMap.get(vo.getId()) == null) {
                resourceVoMap.put(vo.getId(), new ArrayList<>());
            }
            resourceVoMap.get(vo.getId()).add(vo);
        }
        List<ResourceVo> result = new ArrayList<>();
        for (List<ResourceVo> rvs : resourceVoMap.values()) {
            ResourceVo resourceVo = mergeResourceInner(rvs);
            if (resourceVo != null) {
                result.add(resourceVo);
            }
        }
        return result;
    }

    /**
     * 转换
     * @param roleResourcePos
     * @param userGroupResourcePos
     * @param userResourcePos
     * @return
     */
    public static List<ResourceVo> transferResourceVo(List<RoleResourcePo> roleResourcePos, List<UserGroupResourcePo> userGroupResourcePos, List<UserResourcePo> userResourcePos) {
        List<ResourceVo> resourceVos = new ArrayList<>();
        // 需要排除的资源
        Set<Long> excludeResources = new HashSet<>();
        // 需要排除的文件夹
        Map<Long, Set<Long>> excludeFolders = new HashMap<>();
        if (roleResourcePos != null) {
            // 1.相同的资源先进行合并
            Map<Long, List<RoleResourcePo>> roleResourcePoMap = new HashMap<>();
            for (RoleResourcePo po : roleResourcePos) {
                if (roleResourcePoMap.get(po.getId()) == null) {
                    roleResourcePoMap.put(po.getId(), new ArrayList<>());
                }
                roleResourcePoMap.get(po.getId()).add(po);
            }
            for (List<RoleResourcePo> pos : roleResourcePoMap.values()) {
                ResourceVo resourceVo = toResourceVo1(pos);
                if (resourceVo != null) {
                    resourceVos.add(resourceVo);
                }
            }
        }
        if (userGroupResourcePos != null) {
            // 1.相同的资源先进行合并
            Map<Long, List<UserGroupResourcePo>> userGroupResourcePoMap = new HashMap<>();
            for (UserGroupResourcePo po : userGroupResourcePos) {
                if (userGroupResourcePoMap.get(po.getId()) == null) {
                    userGroupResourcePoMap.put(po.getId(), new ArrayList<>());
                }
                userGroupResourcePoMap.get(po.getId()).add(po);
            }
            for (List<UserGroupResourcePo> pos : userGroupResourcePoMap.values()) {
                ResourceVo resourceVo = toResourceVo2(pos);
                if (resourceVo != null) {
                    resourceVos.add(resourceVo);
                }
            }
        }
        if (userResourcePos != null) {
            // 1.相同的资源先进行合并
            Map<Long, List<UserResourcePo>> userResourcePoMap = new HashMap<>();
            for (UserResourcePo po : userResourcePos) {
                if (userResourcePoMap.get(po.getId()) == null) {
                    userResourcePoMap.put(po.getId(), new ArrayList<>());
                }
                userResourcePoMap.get(po.getId()).add(po);
            }
            for (List<UserResourcePo> pos : userResourcePoMap.values()) {
                ResourceVo resourceVo = toResourceVo3(pos, excludeResources, excludeFolders);
                if (resourceVo != null) {
                    resourceVos.add(resourceVo);
                }
            }
        }
        for (int i = resourceVos.size() - 1; i >= 0; i--) {
            ResourceVo resourceVo = resourceVos.get(i);
            // 3. 排除资源的处理
            if (excludeResources.contains(resourceVo.getId())) {
                resourceVos.remove(i);
                continue;
            }
            if (excludeFolders.size() > 0 && excludeFolders.containsKey(resourceVo.getId())) {
                Set<Long> fSet = excludeFolders.get(resourceVo.getId());
                List<FolderAndColumnsVo> folderAndColumns = resourceVo.getFolderAndColumns();
                for (int j = folderAndColumns.size() - 1; j >= 0; j--) {
                    if (fSet.contains(folderAndColumns.get(j).getFolder())) {
                        folderAndColumns.remove(j);
                    }
                }
            }
        }
        // 4. 排除文件夹的处理
        // 5. 排除列的处理
        return resourceVos;
    }

    private static ResourceVo mergeResourceInner(List<ResourceVo> resourceVos) {
        if (resourceVos.size() == 0) {
            return null;
        }
        ResourceVo tmp = resourceVos.get(0);
        // 2.相同的文件夹进行合并处理
        for (ResourceVo po : resourceVos) {
            List<FolderAndColumnsVo> folderAndColumns = po.getFolderAndColumns();
            if (folderAndColumns != null) {
                for (FolderAndColumnsVo fac : folderAndColumns) {
                    assembleFolderAndColumns(tmp, fac);
                }
            }
        }

        return tmp;
    }

    private static ResourceVo toResourceVo1(List<RoleResourcePo> roleResourcePos) {
        if (roleResourcePos.size() == 0) {
            return null;
        }
        ResourceVo tmp = new ResourceVo();
        BeanUtils.copyProperties(roleResourcePos.get(0), tmp);
        // 2.相同的文件夹进行合并处理
        for (RoleResourcePo po : roleResourcePos) {
            assembleFolderAndColumns(tmp, po.getVisualFolderId(), po.getExcludeColumns(), null);
        }

        return tmp;
    }

    private static ResourceVo toResourceVo2(List<UserGroupResourcePo> userGroupResourcePos) {
        if (userGroupResourcePos.size() == 0) {
            return null;
        }
        ResourceVo tmp = new ResourceVo();
        BeanUtils.copyProperties(userGroupResourcePos.get(0), tmp);
        // 2.相同的文件夹进行合并处理
        for (UserGroupResourcePo po : userGroupResourcePos) {
            assembleFolderAndColumns(tmp, po.getVisualFolderId(), po.getExcludeColumns(), null);
        }

        return tmp;
    }

    private static ResourceVo toResourceVo3(List<UserResourcePo> userResourcePos, Set<Long> excludeResources, Map<Long, Set<Long>> excludeFolders) {
        if (userResourcePos.size() == 0) {
            return null;
        }
        ResourceVo tmp = new ResourceVo();
        BeanUtils.copyProperties(userResourcePos.get(0), tmp);
        // 2.相同的文件夹进行合并处理
        for (UserResourcePo po : userResourcePos) {
            // 排除资源
            if (po.getDirect() == -1) {
                excludeResources.add(po.getId());
            } else if (po.getDirect() == -2) {// 排除文件夹
                if (excludeFolders.get(po.getId()) == null) {
                    excludeFolders.put(po.getId(), new HashSet<>());
                }
                excludeFolders.get(po.getId()).add(po.getVisualFolderId());
            } else {
                assembleFolderAndColumns(tmp, po.getVisualFolderId(), po.getExcludeColumns(), po.getIncludeColumns());
            }
        }

        return tmp;
    }

    private static void assembleFolderAndColumns(ResourceVo target, Long folderId, String excludeColumns, String includeColumns) {
        String[] exs = null;
        if (excludeColumns != null) {
            exs = excludeColumns.split(",");
        }
        String[] ins = null;
        if (includeColumns != null) {
            ins = includeColumns.split(",");
        }
        assembleFolderAndColumns(target, new FolderAndColumnsVo(folderId, exs, ins));
    }

    private static void assembleFolderAndColumns(ResourceVo target, FolderAndColumnsVo folderAndColumnsVo) {
        if (target == null || folderAndColumnsVo == null) {
            return;
        }

        if (target.getFolderAndColumns() == null) {
            target.setFolderAndColumns(new ArrayList<>());
            target.getFolderAndColumns().add(folderAndColumnsVo);
        } else if (target.getFolderAndColumns().size() > 0) {
            boolean flag = false;
            List<FolderAndColumnsVo> folderAndColumns = target.getFolderAndColumns();
            for (FolderAndColumnsVo fac : folderAndColumns) {
                if (fac.getFolder().equals(folderAndColumnsVo.getFolder())) {
                    flag = true;
                    mergeResourceInner(fac, folderAndColumnsVo);
                    break;
                }
            }
            if (flag == false) {
                target.getFolderAndColumns().add(folderAndColumnsVo);
            }
        }
    }

    /**
     * 合并两个Resource的FolderAndExcludeColumns
     * 取并集
     *
     * @param target
     * @param source
     * @return
     */
    private static void mergeResourceInner(FolderAndColumnsVo target, FolderAndColumnsVo source) {
        if (target == null || source == null) {
            return;
        }

        // 处理 列的合并  --- exclude
        // 并集
        unionColumns(target, source);
        // 交集
        // intersectionColumns(targetExcludeColumns, anotherFolderColumnsVo);
    }

    /**
     * 取并集
     * 当两个FolderAndColumnsVo的folder相同时，整合columns
     *
     * @param target 目标
     * @param source 合并源
     * @return
     */
    private static void unionColumns(FolderAndColumnsVo target, FolderAndColumnsVo source) {
        // 处理排除的列
        Set<String> columns = target.getExcludeColumns();
        Set<String> anotherColumns = source.getExcludeColumns();
        for (String column : anotherColumns) {
            if (!columns.contains(column)) {
                columns.add(column);
            }
        }
        // 处理包含的列
        Set<String> includeColumns = target.getIncludeColumns();
        Set<String> anotherIncludeColumns = source.getIncludeColumns();
        for (String c : anotherIncludeColumns) {
            includeColumns.add(c);
        }
    }

    /**
     * 取交集
     * 当两个FolderAndColumnsVo的folder相同时，整合columns
     *
     * @param target 目标
     * @param source 合并源
     * @return
     */
    private static void intersectionColumns(FolderAndColumnsVo target, FolderAndColumnsVo source) {
        Set<String> columns = target.getExcludeColumns();
        Set<String> anotherColumns = source.getExcludeColumns();
        Set<String> newColumns = new HashSet<>();
        for (String column : anotherColumns) {
            if (columns.contains(column)) {
                newColumns.add(column);
            }
        }
        target.setExcludeColumns(newColumns);

        // 处理包含的列
        Set<String> includeColumns = target.getIncludeColumns();
        Set<String> anotherIncludeColumns = source.getIncludeColumns();
        for (String c : anotherIncludeColumns) {
            includeColumns.add(c);
        }
    }

}
