
package ru.markov.vkproject.entity;




public class Group {

    private Integer id;
    
    private String name;

    private Integer countMembers;
    
    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountMembers() {
        return countMembers;
    }

    public void setCountMembers(Integer countMembers) {
        this.countMembers = countMembers;
    }
    
    
    
    
    
}
