//package com.solirius.journal.Service;
//
//import com.solirius.journal.model.Plugin;
//import com.solirius.journal.repository.PluginRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PluginService {
//
//    private PluginRepository pluginRepository;
//
//    @Autowired
//    public PluginService(PluginRepository pluginRepository) {
//        this.pluginRepository = pluginRepository;
//    }
//
//    public Optional<Plugin> getPlugin(Integer pluginId) {
//        return pluginRepository.findById(pluginId);
//    }
//
//    public Optional<Plugin> getPlugin(String pluginName) {
//        return pluginRepository.findByName(pluginName);
//    }
//
//    public List<Plugin> getAllPlugins() {
//        return pluginRepository.findAllByOrderByPluginIdAsc();
//    }
//
//    public Plugin createPlugin(Plugin plugin) {
//        return pluginRepository.save(plugin);
//    }
//
//    public void destroyPlugin(Plugin plugin) {
//        pluginRepository.delete(plugin);
//    }
//}
