package stories.simpletab;

import kura.tab.Connections;
import kura.tab.libs.socket.emitter.Emitter;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventGen {
    public static void EventGeneratorRunner(){
        new BukkitRunnable(){
            Long Time_Stamp = System.currentTimeMillis();
            @Override
            public void run(){
                if(Time_Stamp != Connections.IOTMStamp){
                    Time_Stamp = Connections.IOTMStamp;
                    EventGenerator();
                    System.out.println("生成しました。");
                }
            }
        }.runTaskTimer(SimpletabAdditions.plugin,500,100);
    }

    public static void EventGenerator() {
        if (Connections.sio != null) {
            Connections.sio.on("tabad_lp_add", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_lp_add");
                    JSONObject obj = (JSONObject) object[0];
                    try {
                        System.out.println("to: " + obj.getString("to") + " id: " + obj.getString("id"));
                        JSONObject json = new JSONObject();
                        json.put("to", obj.getString("to")).put("id", obj.getInt("id"));
                        //処理
                        if (obj.has("group_name")) {
                            List<String> member = new ArrayList<>();
                            String Permission_Name = obj.getString("group_name");
                            json.put("group_name", Permission_Name);
                            Group group = LuckPermsProvider.get().getGroupManager().getGroup(Permission_Name);
                            if (group != null) {
                                InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                                for (int i = 0; i < obj.getJSONArray("Players").length(); i++) {
                                    String uuid = obj.getJSONArray("Players").get(i).toString();
                                    User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(uuid));
                                    if (user != null) {
                                        DataMutateResult result = user.data().add(node);
                                        if (result.wasSuccessful()) member.add(uuid);
                                    }
                                }
                                json.put("AddPlayers", member);
                            }
                        } else {
                            json.put("group_name", "Error");
                        }
                        Connections.sio.emit("tabad_lp_add", json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_lp_remove", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_lp_remove");
                    JSONObject obj = (JSONObject) object[0];
                    try {
                        System.out.println("to: " + obj.getString("to") + " id: " + obj.getString("id"));
                        JSONObject json = new JSONObject();
                        json.put("to", obj.getString("to")).put("id", obj.getInt("id"));
                        //処理
                        if (obj.has("group_name")) {
                            List<String> member = new ArrayList<>();
                            String Permission_Name = obj.getString("group_name");
                            json.put("group_name", Permission_Name);
                            Group group = LuckPermsProvider.get().getGroupManager().getGroup(Permission_Name);
                            if (group != null) {
                                InheritanceNode node = InheritanceNode.builder(group).value(false).build();
                                for (int i = 0; i < obj.getJSONArray("Players").length(); i++) {
                                    String uuid = obj.getJSONArray("Players").get(i).toString();
                                    User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(uuid));
                                    if (user != null) {
                                        DataMutateResult result = user.data().add(node);
                                        if (result.wasSuccessful()) member.add(uuid);
                                    }
                                }
                                json.put("RemovePlayers", member);
                            }
                        } else {
                            json.put("group_name", "Error");
                        }
                        Connections.sio.emit("tabad_lp_remove", json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_lp_list", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_lp_remove");
                    JSONObject obj = (JSONObject) object[0];
                    try {
                        System.out.println("to: " + obj.getString("to") + " id: " + obj.getString("id"));
                        JSONObject json = new JSONObject();
                        json.put("to", obj.getString("to")).put("id", obj.getInt("id"));
                        //処理
                        if (obj.has("group_name")) {
                            List<String> member = new ArrayList<>();
                            String Permission_Name = obj.getString("group_name");
                            json.put("group_name", Permission_Name);
                            for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                                if (p.getPlayer().hasPermission("group." + Permission_Name)) member.add(p.getName());
                            }
                            json.put("Players", member);
                        }
                        Connections.sio.emit("tabad_lp_list", json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
