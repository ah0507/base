package net.chensee.base.utils;

import lombok.Data;
import net.chensee.base.action.folder.vo.FolderVo;

import java.util.ArrayList;
import java.util.List;

@Data
public class FolderTree {

    private Long id;

    private Long parentId;

    private FolderVo folderVo;

    private List<FolderTree> folderTrees;

    public FolderTree(){
        folderTrees = new ArrayList<>();
    }
}
