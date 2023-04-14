package mc.replay.common.utils.config;

import lombok.Getter;
import mc.replay.api.utils.config.IReplayConfigProcessor;
import mc.replay.api.utils.config.SimpleConfigurationFile;
import mc.replay.api.utils.config.templates.IReplayConfigStructure;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Getter
public class ReplayConfigProcessor<T extends Enum<? extends IReplayConfigStructure>> implements IReplayConfigProcessor<T> {

    private final Class<T> enumClass;
    private final SimpleConfigurationFile configurationFile;

    private final List<IReplayConfigStructure> presentStructures;
    private final Map<IReplayConfigStructure, Object> loadedStructures;

    public ReplayConfigProcessor(JavaPlugin plugin, String filename, Class<T> enumClass) throws Exception {
        this.enumClass = enumClass;

        List<? extends Enum<? extends IReplayConfigStructure>> collect = Arrays.stream(this.enumClass.getEnumConstants()).toList();

        List<IReplayConfigStructure> settingsStructure = new ArrayList<>();
        for (Enum<? extends IReplayConfigStructure> enumConst : collect) {
            IReplayConfigStructure structure = (IReplayConfigStructure) enumConst;
            settingsStructure.add(structure);
        }

        this.configurationFile = new SimpleConfigurationFile(plugin, filename);

        this.presentStructures = settingsStructure;
        this.loadedStructures = new HashMap<>();

//        this.removeUnusedStructures();
        this.load();
    }

    @Override
    public void set(T type, Object value) throws Exception {
        IReplayConfigStructure structure = (IReplayConfigStructure) type;

        try {
            Object valueCasted = structure.getDefaultValue().getClass().cast(value);

            this.loadedStructures.put(structure, valueCasted);
            this.configurationFile.set(structure.getPath(), valueCasted);
        } catch (Exception exception) {
            throw new Exception("Error while setting " + this.configurationFile.getName() + " (" + structure.getPath() + "). The given value must be a " + structure.getDefaultValue().getClass().getSimpleName().toLowerCase() + ".");
        }
    }

    @Override
    public Object get(T type) {
        IReplayConfigStructure structure = (IReplayConfigStructure) type;
        return loadedStructures.getOrDefault(structure, structure.getDefaultValue());
    }

    @Override
    public String getString(T type) {
        return (String) this.get(type);
    }

    @Override
    public boolean getBoolean(T type) {
        return (Boolean) this.get(type);
    }

    @Override
    public int getInteger(T type) {
        return (Integer) this.get(type);
    }

    @SafeVarargs
    private void load(T... types) throws Exception {
        List<IReplayConfigStructure> structureList = types.length == 0 ? this.presentStructures : Arrays.stream(types).map(t -> ((IReplayConfigStructure) t)).toList();

        boolean changed = false;

        for (IReplayConfigStructure structure : structureList) {
            try {
                if (!this.configurationFile.contains(structure.getPath())) {
                    this.configurationFile.set(structure.getPath(), structure.getDefaultValue());
                    this.loadedStructures.put(structure, structure.getDefaultValue());
                    changed = true;
                    continue;
                }

                Object value = this.configurationFile.get(structure.getPath(), structure.getDefaultValue());
                this.loadedStructures.put(structure, structure.getDefaultValue().getClass().cast(value));
            } catch (Exception exception) {
                this.loadedStructures.put(structure, structure.getDefaultValue());
                System.out.println("Error while loading " + this.configurationFile.getName() + " (" + structure.getPath() + "). The given value must be a " + structure.getDefaultValue().getClass().getSimpleName().toLowerCase() + ".");
            }
        }

        if (changed) this.configurationFile.save();
    }

    @SafeVarargs
    @Override
    public final void reload(T... types) throws Exception {
        this.load(types);
    }

    private void removeUnusedStructures() throws Exception {
        for (String path : this.configurationFile.getKeys(true)) {
            if (getSettingsStructureByPath(path) == null) {
                this.configurationFile.set(path, null);
            }
        }

        this.configurationFile.save();
    }

    private IReplayConfigStructure getSettingsStructureByPath(String path) {
        for (IReplayConfigStructure structure : this.presentStructures) {
            if (structure.getPath().equalsIgnoreCase(path)) return structure;
        }

        return null;
    }
}
