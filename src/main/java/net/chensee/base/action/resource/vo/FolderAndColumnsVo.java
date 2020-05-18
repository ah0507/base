package net.chensee.base.action.resource.vo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xx
 * @program base
 * @date 2019-09-16 14:53
 * @description 文件夹和排除的列
 */
@Data
public class FolderAndColumnsVo {

    private Long folder;
    private String folderName;

    private Set<String> excludeColumns;
    /**
     * 只有user和resource相关联时有用
     */
    private Set<String> includeColumns;

    public FolderAndColumnsVo(Long folder) {
        this(folder, new HashSet<>());
    }

    public FolderAndColumnsVo(Long folder, Set<String> excludeColumns) {
        this.folder = folder;
        this.excludeColumns = excludeColumns;
    }

    public FolderAndColumnsVo(Long folder, String[] excludeColumns, String[] includeColumns) {
        this.folder = folder;
        this.excludeColumns = new HashSet<>();
        this.includeColumns = new HashSet<>();
        merge(this.excludeColumns, excludeColumns);
        merge(this.includeColumns, includeColumns);
    }

    protected static void merge(Set<String> container, String[] columns) {
        if (columns != null) {
            for (String s : columns) {
                container.add(s);
            }
        }
    }

    public FolderAndColumnsVo(Long folder, Set<String> excludeColumns, Set<String> includeColumns) {
        this.folder = folder;
        this.excludeColumns = excludeColumns;
        this.includeColumns = includeColumns;
    }

}
