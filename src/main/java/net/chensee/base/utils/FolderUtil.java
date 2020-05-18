package net.chensee.base.utils;

import net.chensee.base.action.folder.vo.FolderVo;

import java.util.ArrayList;
import java.util.List;

public class FolderUtil {

    /**
     * 生成文件夹树
     * @param allFolder
     * @return
     */
    public static List<FolderTree> getFolderTrees(List<FolderVo> allFolder) {
        List<FolderTree> folderTrees = new ArrayList<>();
        for (FolderVo folderVo : allFolder) {
            Long parentId = folderVo.getParentId();
            if (parentId == -1) {
                FolderTree tree = new FolderTree();
                tree.setId(folderVo.getId());
                tree.setParentId(parentId);
                tree.setFolderVo(folderVo);
                folderTrees.add(putTree(allFolder, tree));
            }
        }
        return folderTrees;
    }

    private static FolderTree putTree(List<FolderVo> allFolder, FolderTree tree) {
        for (FolderVo folderVo : allFolder) {
            Long parentId = folderVo.getParentId();
            if (tree.getId().equals(parentId)) {
                FolderTree newTree = new FolderTree();
                newTree.setId(folderVo.getId());
                newTree.setParentId(parentId);
                tree.getFolderTrees().add(newTree);
                putTree(allFolder, newTree);
            }
        }
        return tree;
    }

    /**
     * 规则：
     * 如果选中了子文件夹和它的上级（包括多级）则默认存入选中的最上级文件夹
     * @param folderIds
     * @return
     */
    public static List<Long> getFolderIds(List<FolderTree> allFolderTree, List<Long> folderIds) throws Exception {
        //得到选中的文件夹的节点集合
        List<FolderTree> selectFolderTrees = new ArrayList<>();
        for (Long folderId : folderIds) {
            FolderTree folderTree = getFolderTree(allFolderTree, folderId);
            selectFolderTrees.add(folderTree);
        }
        List<Long> getFolderIds = new ArrayList<>();
        for (FolderTree selectCurrentFolderTree : selectFolderTrees) {
            //检查文件夹之间是否存在父子级或者跨级父子关系
            boolean selectFolderId = findSelectFolderId(allFolderTree, selectFolderTrees, selectCurrentFolderTree);
            if (selectFolderId) {
                getFolderIds.add(selectCurrentFolderTree.getId());
            }else {
                //return new ArrayList<>();
            }
        }
        return getFolderIds;
    }

    /**
     *  判断选中每一个文件夹的上级文件夹是否在所选的文件夹集合中（一直判断到根文件夹），
     *  如果没有找到则将当前文件夹id加入到新的所选文件夹集合容器中
     *  [不允许同时存入拥有父子级关系的文件夹，抛出异常（不符合勾选规则）]
     */
    private static boolean findSelectFolderId(List<FolderTree> allFolderTree, List<FolderTree> selectFolderTrees, FolderTree selectCurrentFolderTree) throws Exception {
        Long parentId = selectCurrentFolderTree.getParentId();
        FolderTree parentFolderTree = getFolderTree(allFolderTree, parentId);
        if (parentFolderTree == null) {
            return true;
        }else{
            FolderTree isHaveFolderTree = getSelectFolderTree(selectFolderTrees, parentId);
            if (isHaveFolderTree == null) {
                return findSelectFolderId(allFolderTree, selectFolderTrees, parentFolderTree);
            } else {
                //说明文件夹中同时有存在父子级关系的文件夹
                throw new Exception("不符合勾选规则，拥有父子关系或跨级父子关系的文件夹不能同时被选中");
            }
        }
    }

    private static FolderTree getSelectFolderTree(List<FolderTree> selectFolderTrees, Long id) {
        FolderTree folderTree = null;
        for (FolderTree selectFolderTree : selectFolderTrees) {
            if (id.equals(selectFolderTree.getId())) {
                folderTree = selectFolderTree;
                break;
            }
        }
        return folderTree;
    }

    private static FolderTree getFolderTree(List<FolderTree> folderTrees, Long folderId) {
        FolderTree folderTree = null;
        for (FolderTree currentFolderTree : folderTrees) {
            Long id = currentFolderTree.getId();
            if (folderId.equals(id)) {
                folderTree = currentFolderTree;
                break;
            }else{
                List<FolderTree> folderTrees1 = currentFolderTree.getFolderTrees();
                if (folderTrees1.size() > 0) {
                    FolderTree folderTree1 = getFolderTree(folderTrees1, folderId);
                    if (folderTree1 != null) {
                        folderTree = folderTree1;
                        break;
                    }
                }
            }
        }
        return folderTree;
    }

    public static void main(String[] args) throws Exception {
        List<FolderVo> allFolder = new ArrayList<>();
        FolderVo folderVo1 = new FolderVo();
        folderVo1.setId(1L);
        folderVo1.setParentId(-1L);
        FolderVo folderVo2 = new FolderVo();
        folderVo2.setId(2L);
        folderVo2.setParentId(1L);
        FolderVo folderVo3 = new FolderVo();
        folderVo3.setId(3L);
        folderVo3.setParentId(1L);
        FolderVo folderVo4 = new FolderVo();
        folderVo4.setId(4L);
        folderVo4.setParentId(1L);
        FolderVo folderVo5 = new FolderVo();
        folderVo5.setId(5L);
        folderVo5.setParentId(3L);
        FolderVo folderVo6 = new FolderVo();
        folderVo6.setId(6L);
        folderVo6.setParentId(3L);
        FolderVo folderVo7 = new FolderVo();
        folderVo7.setId(7L);
        folderVo7.setParentId(2L);
        FolderVo folderVo8 = new FolderVo();
        folderVo8.setId(8L);
        folderVo8.setParentId(2L);
        FolderVo folderVo9 = new FolderVo();
        folderVo9.setId(9L);
        folderVo9.setParentId(5L);
        FolderVo folderVo10 = new FolderVo();
        folderVo10.setId(10L);
        folderVo10.setParentId(5L);
        allFolder.add(folderVo1);
        allFolder.add(folderVo2);
        allFolder.add(folderVo3);
        allFolder.add(folderVo4);
        allFolder.add(folderVo5);
        allFolder.add(folderVo6);
        allFolder.add(folderVo7);
        allFolder.add(folderVo8);
        allFolder.add(folderVo9);
        allFolder.add(folderVo10);
        List<Long> longs = new ArrayList<>();
        longs.add(10L);
        longs.add(5L);
        longs.add(7L);
        List<FolderTree> folderTrees = FolderUtil.getFolderTrees(allFolder);
        List<Long> folderIds1 = FolderUtil.getFolderIds(folderTrees, longs);
        System.out.println(folderIds1);

    }
}
