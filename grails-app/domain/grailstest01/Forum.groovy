package grailstest01

import javax.xml.crypto.Data

class Forum {

    String name
    String description
    Data lastPost
    List topics
    static hasMany = [ topics : Topic]

    static mapping = {
        topics joinTable: false, column: 'Forum_ID' //one to many by foreign key

        table 'forums' //table' name
        name column: 'Forum_Name' //change the "name" property column name
        lastPost type: 'timestamp' //change the column type
    }

    static constraints = {
        description(nullable: true)
    }



}
