package grailstest01

/**
 * User and UserDetail is one-to-one
 *
 */
class User {

    String firstName
    String lastName
    String password
    String email
    String cellPhoneNo
    UserType userType

   // UserDetail userDetail

    //Defines a bidirectional one-to-one association between two classes where the foreign key is in the child.
    static hasOne = [userDetail : UserDetail]

    static mapping = {
        table 'users_base'
        firstName column: 'first_name', sqlType: 'varchar(20)', index: 'firstName_Idx'
        lastName column: 'lastName', sqlType: 'varchar(20)'
        password column: 'password', sqlType: 'varchar(30)'
        email column: 'email', sqlType: 'varchar(50)'
        cellPhoneNo column: 'cellPhoneNo', sqlType: 'varchar(30)'

        role column: 'role', sqlType: 'int', comment: '0-admin, 1-provider, 2-customer', defaultValue: 1
    }

    static constraints = {
        //userDetail(unique: true)  //one-to-one
        cellPhoneNo nullable: false, unique: true, matches: /\d+/
        password nullable: false, unique: false
        email nullable: true
        userDetail nullable: true

    }

    //static hasMany = [topics : Topic, posts : Post]

}
