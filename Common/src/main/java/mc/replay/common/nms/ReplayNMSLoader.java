package mc.replay.common.nms;

import com.google.common.reflect.ClassPath;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ReplayNMSLoader {

    public static List<Class<?>> getNMSClasses(JavaPlugin plugin, String packagePath) throws Exception {
        List<Class<?>> classes = new ArrayList<>();

        for (ClassPath.ClassInfo classInfo : ClassPath.from(plugin.getClass().getClassLoader()).getTopLevelClassesRecursive(packagePath)) {
            classes.add(Class.forName(classInfo.getName()));
        }

        return classes;
    }
}
