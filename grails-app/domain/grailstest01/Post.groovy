package grailstest01

class Post {
    static mapping = {
        id generator: 'uuid'
    }

    String message
    Date date

    static constraints = {
    }
}
