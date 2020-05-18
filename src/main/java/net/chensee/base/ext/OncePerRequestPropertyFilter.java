package net.chensee.base.ext;

import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.resource.vo.FolderAndColumnsVo;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.common.Folderable;
import net.chensee.base.common.Pair;
import net.chensee.base.common.vo.DataColumnVo;
import net.chensee.base.constants.BaseConstants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author xx
 * @program base
 * @date 2019-09-11 14:16
 * @description
 */
@Slf4j
public class OncePerRequestPropertyFilter {

    private final Object obj;
    private final String property;
    private final Object value;
    private final HttpServletRequest request;

    private Set<String> allColumns;
    private Map<Long, Set<String>> exs;

    public OncePerRequestPropertyFilter(Object obj, String property, Object value, HttpServletRequest request) {
        this.obj = obj;
        this.property = property;
        this.value = value;
        this.request = request;
        init();
    }

    private void init() {
        allColumns = (Set<String>) request.getAttribute(BaseConstants.CurrentResourceAllColumnsKey);
        exs = (Map<Long, Set<String>>) request.getAttribute(BaseConstants.CurrentResourceExcludeColumnsKey);

    }

    public boolean filter() {
        if (needFilter() && hasFolder()) {
            log.debug("序列化对象集成了Folderable，需要按照规则过滤");
            return inAllColumns() && !needToExcludeColumn();
        }
        return true;
    }

    /**
     * 是否接口需要排除的列
     *
     * @return
     */
    private boolean needToExcludeColumn() {
        if (!folderValEq()) {
            return false;
        }
        Long folderId = ((Folderable) obj).getFolderId();
        if (exs == null) {
            return false;
        }
        Set<String> excludeColumns = exs.get(folderId);
        boolean result = false;
        for (String c : excludeColumns) {
            result = calcExcludeColumn(c);
            if (result) {
                break;
            }
        }
        return result;
    }

    /**
     * 将配置的排除列与实际列进行比较
     *
     * @param needExColumn
     * @return
     */
    private boolean calcExcludeColumn(String needExColumn) {
        Pair<String, String> pair = getClassNameInProperty(needExColumn);
        // pair 为null ，代表没有配置classFullName限定
        if (pair == null) {
            return property.equals(needExColumn);
        }
        switch (needFilterByFullClassName(pair.getR())) {
            // classFullName配置不正确时 和 匹配上了时，统一按过滤处理
            case -1:
            case 1:
                if (property.equals(pair.getT())) {
                    return true;
                }
                break;
            // classFullName配置正确,但未匹配上时，不做过滤处理
            case 0:
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 是否接口需要返回的列
     *
     * @return
     */
    private boolean inAllColumns() {
        return allColumns != null && allColumns.contains(property);
    }

    public boolean needFilter() {
        return allColumns != null && allColumns.size() > 0;
    }

    /**
     * 判断是否继承了Folderable
     *
     * @return
     */
    private boolean hasFolder() {
        return Folderable.class.isInstance(obj);
    }

    /**
     * 判断文件夹的值是否相等
     *
     * @return
     */
    private boolean folderValEq() {
        Long folderId = ((Folderable) obj).getFolderId();
        if (folderId != null && exs != null && exs.containsKey(folderId)) {
            log.debug("文件夹{}需要过滤的列为（{}）", folderId, exs.get(folderId));
            return true;
        }
        return false;
    }

    /**
     * 获取类的全程
     * <p>
     * 配置的格式为 --- name{classFullName} 或者 {classFullName}name
     *
     * @param column
     * @return
     */
    private Pair<String, String> getClassNameInProperty(String column) {
        int si = column.indexOf("{");
        if (si == -1) {
            return null;
        }
        int ei = column.lastIndexOf("}");
        if (ei == -1) {
            return null;
        }
        Pair<String, String> pair = new Pair<>();
        pair.setR(column.substring(si, ei).trim());
        if (si == 0) {
            pair.setT(column.substring(ei));
        } else {
            pair.setT(column.substring(0, si));
        }
        return pair;
    }

    /**
     * 过滤字段上是否配置了，需要按照类的全程来过滤的
     * <p>
     * -1 --- 代表classFullName限定配置不正确
     * 0 --- 代表classFullName限定配置正确，但未匹配上了
     * 1 --- 代表classFullName限定配置正确，且匹配上了
     *
     * @return
     */
    private int needFilterByFullClassName(String classFullName) {
        if (classFullName != null) {
            try {
                Class<?> aClass = Class.forName(classFullName);
                return aClass.isInstance(obj) ? 1 : 0;
            } catch (ClassNotFoundException e) {
                log.warn("未找到正确的类{}！", classFullName);
                return -1;
            }
        }
        return -1;
    }
}
