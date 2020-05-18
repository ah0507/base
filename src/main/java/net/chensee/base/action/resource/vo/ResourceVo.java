package net.chensee.base.action.resource.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.vo.DataColumnVo;

import java.util.*;

/**
 * @author xx
 * @program base
 * @date 2019-09-16 18:09
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceVo extends BaseResourceVo {
    /**
     * 来源
     */
    private Map<String, List<Long>> source;

    /**
     * 可视文件夹ID 以及 每个文件夹需要排除的列
     * <p>
     * 此字段可以不返回到前端，前端不需要根据文件夹来区分排除字段
     */
    private List<FolderAndColumnsVo> folderAndColumns;
    /**
     * 前端使用此字段来获取显示的字段
     */
    private List<DataColumnVo> visualColumns;

    protected void mergeFolderAndExColumns(ResourceVo target, Long folderId, String excludeColumns) {
        if (excludeColumns != null) {
            String[] strings = excludeColumns.split(",");
            if (target.getFolderAndColumns() == null) {
                target.setFolderAndColumns(new ArrayList<>());
            }
            target.getFolderAndColumns().add(new FolderAndColumnsVo(folderId, strings, null));
        }
    }

    /**
     * 查找是否包含相同的文件夹
     *
     * @param folderId
     * @return
     */
    public boolean contain(Long folderId) {
        FolderAndColumnsVo excludeColumns = this.findExcludeColumns(folderId);
        return excludeColumns == null ? false : true;
    }

    /**
     * 查找文件夹排除的列
     *
     * @param folderId
     * @return
     */
    public FolderAndColumnsVo findExcludeColumns(Long folderId) {
        if (folderId == null || this.getFolderAndColumns() == null || this.getFolderAndColumns().size() == 0) {
            return null;
        }
        for (FolderAndColumnsVo folderColumnsVo : this.getFolderAndColumns()) {
            Long folder = folderColumnsVo.getFolder();
            if (folderId.equals(folder)) {
                return folderColumnsVo;
            }
        }
        return null;
    }

    public List<DataColumnVo> getVisualColumns() {
        if (this.getColumns() == null) {
            return null;
        }
        if (this.getFolderAndColumns() == null || this.getFolderAndColumns().size() == 0) {
            return this.getColumns();
        }
        Map<String, Integer> excludeColumnsCount = new HashMap<>();
        for (FolderAndColumnsVo fac : this.getFolderAndColumns()) {
            Set<String> excludeColumns = fac.getExcludeColumns();
            for (String ec : excludeColumns) {
                if (! excludeColumnsCount.containsKey(ec)) {
                    excludeColumnsCount.put(ec, 1);
                } else {
                    excludeColumnsCount.put(ec, excludeColumnsCount.get(ec) + 1);
                }
            }
        }

        // 字段出现的次数正好等于文件夹的个数时，要排除
        Set<String> needExcludeColumns = new HashSet<>();
        for (String ec : excludeColumnsCount.keySet()) {
            if (excludeColumnsCount.get(ec).equals(this.getFolderAndColumns().size())) {
                needExcludeColumns.add(ec);
            }
        }
        // 字段如果出现再includes中，则不能被排除
        for (FolderAndColumnsVo fac : this.getFolderAndColumns()) {
            Set<String> includeColumns = fac.getIncludeColumns();
            for (String ic : includeColumns) {
                if (needExcludeColumns.contains(ic)) {
                    needExcludeColumns.remove(ic);
                }
            }
        }

        List<DataColumnVo> result = new ArrayList<>(this.getColumns().size() - needExcludeColumns.size());
        for (DataColumnVo dc : this.getColumns()) {
            if (! needExcludeColumns.contains(dc.getKey())) {
                result.add(dc);
            }
        }
        return result;

    }
}
