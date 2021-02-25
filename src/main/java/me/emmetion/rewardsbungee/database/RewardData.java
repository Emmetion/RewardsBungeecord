package me.emmetion.rewardsbungee.database;

public class RewardData {
    private String uuid;
    private int tier1count, tier2count, tier3count;
    private boolean changed = false;


    public RewardData() {

    }

    public RewardData(String uuid, int tier1count, int tier2count, int tier3count) {
        this.uuid = uuid;
        this.tier1count = tier1count;
        this.tier2count = tier2count;
        this.tier3count = tier3count;
    }

    public void print(){
        System.out.println(uuid);
        System.out.println(tier1count);
        System.out.println(tier2count);
        System.out.println(tier3count);
    }

    public boolean getChanged(){
        return changed;
    }

    public void setChanged(boolean b){
        changed = b;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        setChanged(true);
    }

    public int getTier1count() {
        return tier1count;
    }

    public void setTier1count(int tier1count) {
        this.tier1count = tier1count;
        setChanged(true);
    }

    public int getTier2count() {
        return tier2count;
    }

    public void setTier2count(int tier2count) {
        this.tier2count = tier2count;
        setChanged(true);
    }

    public int getTier3count() {
        return tier3count;
    }

    public void setTier3count(int tier3count) {
        this.tier3count = tier3count;
        setChanged(true);
    }
}
