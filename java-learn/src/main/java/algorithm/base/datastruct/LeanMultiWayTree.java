package algorithm.base.datastruct;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * 多叉树
 */
public class LeanMultiWayTree {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        Map<String, List<String>> dependencyMap = new HashMap<>();
        dependencyMap.put("a", Arrays.asList(new String[] {"b", "c"}));
        dependencyMap.put("b", Arrays.asList(new String[] {"d", "c"}));
        dependencyMap.put("c", Arrays.asList(new String[] {"e", "f", "g"}));
        dependencyMap.put("d", Arrays.asList(new String[] {}));
        dependencyMap.put("e", Arrays.asList(new String[] {}));
        dependencyMap.put("f", Arrays.asList(new String[] {}));
        dependencyMap.put("g", Arrays.asList(new String[] {}));

        // 构建多叉树
        TreeNode<String> root = getDependencyTree(dependencyMap, "a");

        // 打印多叉树
        System.out.println(mapper.writeValueAsString(root));
    }

    private static <T> TreeNode<T> getDependencyTree(Map<T, List<T>> content, T rootValue)
        throws IOException {
        if (null==rootValue || null==content || !content.containsKey(rootValue))
            return null;
        TreeNode<T> root = new TreeNode<T>(rootValue, null);
        Deque<TreeNode<T>> list = new LinkedList<>();
        list.push(root); // 添加根节点
        // 深度优先原则添加
        while (!list.isEmpty()) {
            TreeNode<T> node = list.poll();
            List<T> values = content.get(node.value);
            for (T value: values) {
                if (hasCircularDependency(node, value)) {
                    throw new IOException("存在循环依赖 " +
                            String.valueOf(node.value) + ":" + String.valueOf(value));
                }
            }
            content.get(node.value).stream()
                    .map(e -> new TreeNode<T>(e, node))
                    .forEach(node.children::add);
            node.children.forEach(list::push);
        }
        return root;
    }

    /**
     * 检查是否存在循环依赖
     * <br/>
     * 子节点中的值如果出现在父节点中，则认为存在循环依赖
     *
     * @param root
     * @param childrenValue
     * @param <T>
     * @return
     */
    private static <T> boolean hasCircularDependency(TreeNode<T> root, T childrenValue) {
        if (null==root || null==childrenValue)
            return false;
        TreeNode<T> node = root;
        while (null != node) {
            if (childrenValue.equals(node.value))
                return true;
            node = node.parent; // 搜索父节点
        }
        return false;
    }

    /**
     * 多叉树
     * @param <T>
     */
    public static class TreeNode<T> {

        private T value;

        // 父节点
        private TreeNode<T> parent;

        // 子节点
        private List<TreeNode<T>> children = new LinkedList<>();

        public TreeNode(T value, TreeNode<T> parent) {
            this.value = value;
            this.parent = parent;
        }

        public T getValue() {
            return this.value;
        }

        public T getParent() {
            return this.parent==null ? null : this.parent.value;
        }

        public List<TreeNode<T>> getChildren() {
            return this.children;
        }

    }

    /**
     * 检索值
     * @param root
     * @param value
     * @param <T>
     * @return
     */
    public static <T> boolean contains(TreeNode<T> root, T value) {
        if (null==root || null==value)
            return false;
        LinkedList<TreeNode<T>> list = new LinkedList<>();
        list.offer(root); // 添加根节点
        while(!list.isEmpty()) {
            TreeNode<T> node = list.remove();
            if (value.equals(node.value))
                return true;
            node.children.forEach(list::offer);
        }

        return false;
    }

    /**
     * 扁平化
     * @param root
     * @param reverse
     * @param <T>
     * @return
     */
    public static <T> List<TreeNode<T>> flat(TreeNode<T> root, boolean reverse) {
        LinkedList<TreeNode<T>> result = new LinkedList<>();
        if (null==root)
            return null;
        LinkedList<TreeNode<T>> list = new LinkedList<>();
        list.offer(root); // 添加根节点
        while(!list.isEmpty()) {
            TreeNode<T> node = list.remove();
            if (reverse) {
                result.addLast(node);
            } else {
                result.addFirst(node);
            }
            node.children.forEach(list::offer);
        }
        return result;
    }

    /**
     * 查找节点
     * @param root
     * @param value
     * @param <T>
     * @return
     */
    public static <T> TreeNode<T> lookup(TreeNode<T> root, T value) {
        if (null==root || null==value)
            return null;
        LinkedList<TreeNode<T>> list = new LinkedList<>();
        list.offer(root); // 添加根节点
        while(!list.isEmpty()) {
            TreeNode<T> node = list.remove();
            if (value.equals(node.value))
                return node;
            node.children.forEach(list::offer);
        }
        return null;
    }

}
