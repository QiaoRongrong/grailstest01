package grailstest01


class Topic {

    String subject
    String message
    Date date

    static belongsTo = [forum : Forum]  // bidirectional one-to-many

    static constraints = {
    }

    //static hasMany = [posts : Post]

}
