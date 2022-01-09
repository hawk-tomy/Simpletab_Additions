package stories.simpletab;

import VMPlugin.API.VMSystemAPI;
import VMPlugin.Data.PersonalData;
import VMPlugin.Ranks.Rank;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kura.tab.communication.Connections;
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
import java.util.concurrent.ExecutionException;

public class EventGen {

    public static List<k> adds = new ArrayList<>();
    public static List<k> removes = new ArrayList<>();
    public static List<k> deletes = new ArrayList<>();

    public static void EventGeneratorRunner(){
        new BukkitRunnable(){
            Long Time_Stamp = System.currentTimeMillis();
            @Override
            public void run(){
                if(Time_Stamp != Connections.IOTMStamp){
                    Time_Stamp = Connections.IOTMStamp;
                    EventGenerator();
                    System.out.println("Simpletab_Ad_Generate");
                }
            }
        }.runTaskTimer(SimpletabAdditions.plugin,100,100);
    }

    public static void EventGenerator() {
        if (Connections.sio != null) {
            Connections.sio.on("tabad_lp_add", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_lp_add");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        if (Connections.Get_has(object[0],"group_name")) {
                            List<String> member = new ArrayList<>();
                            String Permission_Name = Connections.getJString(object[0],"group_name");
                            json.put("group_name", Permission_Name);
                            Group group = LuckPermsProvider.get().getGroupManager().getGroup(Permission_Name);
                            if (group != null) {
                                InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                                for (int i = 0; i < Connections.getStringArray(object[0],"Players").size(); i++) {
                                    String uuid = Connections.getStringArray(object[0],"Players").get(i).toString();
                                    if(UUID.fromString(uuid) != null) {
                                        User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(uuid));
                                        if(user == null)user = LuckPermsProvider.get().getUserManager().loadUser(UUID.fromString(uuid)).get();
                                        if (user != null) {
                                            DataMutateResult result = user.data().add(node);
                                            if (result.wasSuccessful()) member.add(uuid);
                                            LuckPermsProvider.get().getUserManager().saveUser(user);
                                        }else {
                                            System.out.println("[Simpletab_adds] this User not found");
                                            System.out.println(uuid);
                                        }
                                    }
                                }
                                json.put("AddPlayers", member);
                            }
                        } else {
                            json.put("group_name", "Error");
                        }
                        Connections.sendJson("tabad_lp_add", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_lp_remove", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_lp_remove");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        if (Connections.Get_has(object[0],"group_name")) {
                            List<String> member = new ArrayList<>();
                            String Permission_Name = Connections.getJString(object[0],"group_name");
                            json.put("group_name", Permission_Name);
                            Group group = LuckPermsProvider.get().getGroupManager().getGroup(Permission_Name);
                            if (group != null) {
                                InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                                for (int i = 0; i < Connections.getStringArray(object[0],"Players").size(); i++) {
                                    String uuid = Connections.getStringArray(object[0],"Players").get(i).toString();
                                    if(UUID.fromString(uuid) != null) {
                                        User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(uuid));
                                        if(user == null)user = LuckPermsProvider.get().getUserManager().loadUser(UUID.fromString(uuid)).get();
                                        if (user != null) {
                                            DataMutateResult result = user.data().remove(node);
                                            if (result.wasSuccessful()) member.add(uuid);
                                            LuckPermsProvider.get().getUserManager().saveUser(user);
                                        }
                                    }
                                }
                                json.put("RemovePlayers", member);
                            }
                        } else {
                            json.put("group_name", "Error");
                        }
                        Connections.sendJson("tabad_lp_remove", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_lp_list", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_lp_list");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        if (Connections.Get_has(object[0],"group_name")) {
                            List<String> member = new ArrayList<>();
                            String Permission_Name = Connections.getJString(object[0],"group_name");
                            json.put("group_name", Permission_Name);
                            Group group = LuckPermsProvider.get().getGroupManager().getGroup(Permission_Name);
                            for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                                User user = LuckPermsProvider.get().getUserManager().getUser(UUID.fromString(p.getUniqueId().toString()));
                                if(user == null)user = LuckPermsProvider.get().getUserManager().loadUser(p.getUniqueId()).get();
                                if(user != null && group != null){
                                    if(user != null) {
                                        if (user.getInheritedGroups(user.getQueryOptions()).contains(group))
                                            member.add(p.getUniqueId().toString());
                                    }
                                }
                            }
                            json.put("Players", member);
                        }
                        Connections.sendJson("tabad_lp_list", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_VM_add", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_VM_add");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        String name = Connections.getJString(object[0],"Player");
                        Integer second = Connections.getJInt(object[0],"time");
                        Integer type = Connections.getJInt(object[0],"runk");
                        if(name != ""){
                            Rank rank = Rank.VIP;
                            if(type == 1)rank = Rank.MVP;
                            adds.add(new k(UUID.fromString(name),rank,second));
                            json.put("Player",name);
                        }

                        Connections.sendJson("tabad_VM_add", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_VM_remove", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_VM_remove");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        String name = Connections.getJString(object[0],"Player");
                        Integer second = Connections.getJInt(object[0],"time");
                        Integer type = Connections.getJInt(object[0],"runk");
                        if(name != ""){
                            Rank rank = Rank.VIP;
                            if(type == 1)rank = Rank.MVP;
                            removes.add(new k(UUID.fromString(name),rank,second));
                            json.put("Player",name);
                        }

                        Connections.sendJson("tabad_VM_remove", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_VM_delete", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_VM_delete");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        String name = Connections.getJString(object[0],"Player");
                        Integer type = Connections.getJInt(object[0],"runk");
                        if(name != ""){
                            Rank rank = Rank.VIP;
                            if(type == 1)rank = Rank.MVP;
                            deletes.add(new k(UUID.fromString(name),rank,null));
                            json.put("Player",name);
                        }

                        Connections.sendJson("tabad_VM_delete", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_VM_info", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_VM_info");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        String name = Connections.getJString(object[0],"Player");
                        if(name != ""){
                            if(VMSystemAPI.getPersonalData(UUID.fromString(name)) != null) {
                                Gson gson = new Gson();
                                json.put("data",gson.toJson(VMSystemAPI.getPersonalData(UUID.fromString(name))));
                                json.put("Player", name);
                            }
                        }
                        Connections.sendJson("tabad_VM_info", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Connections.sio.on("tabad_VM_list", new Emitter.Listener() {
                @Override
                public void call(Object... object) {
                    System.out.println("[SimpleAdd] tabad_VM_list");
                    try {
                        System.out.println("to: " + Connections.getJString(object[0],"to") + " id: " + Connections.getJString(object[0],"id"));
                        JSONObject json = new JSONObject();
                        json.put("to", Connections.getJString(object[0],"to")).put("id", Connections.getJInt(object[0],"id"));
                        //??
                        List<PersonalData> datas = new ArrayList<>();
                        Gson gson = new Gson();
                        for(PersonalData data : VMSystemAPI.getAllPersonalData().values())datas.add(data);
                        json.put("data",gson.toJson(datas));

                        Connections.sendJson("tabad_VM_list", json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void run_task(){
        new BukkitRunnable(){
            Long Time_Stamp = System.currentTimeMillis();
            @Override
            public void run(){
                if(adds.size() > 0){
                    for( k a : adds) {
                        VMSystemAPI.addRank(a.uuid, a.runk, a.second);
                    }
                    adds = new ArrayList<>();
                }
                if(removes.size() > 0){
                    for( k a : removes) {
                        VMSystemAPI.removeRank(a.uuid, a.runk, a.second);
                    }
                    removes = new ArrayList<>();
                }

                if(deletes.size() > 0){
                    for( k a : deletes) {
                        VMSystemAPI.deleteRank(a.uuid,a.runk);
                    }
                    deletes = new ArrayList<>();
                }
            }
        }.runTaskTimer(SimpletabAdditions.plugin,100,20);
    }
}
