package grailstest01

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserDetailController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserDetail.list(params), model:[userDetailCount: UserDetail.count()]
    }

    def show(UserDetail userDetail) {
        respond userDetail
    }

    def create() {
        respond new UserDetail(params)
    }

    @Transactional
    def save(UserDetail userDetail) {
        if (userDetail == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userDetail.errors, view:'create'
            return
        }

        userDetail.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), userDetail.id])
                redirect userDetail
            }
            '*' { respond userDetail, [status: CREATED] }
        }
    }

    def edit(UserDetail userDetail) {
        respond userDetail
    }

    @Transactional
    def update(UserDetail userDetail) {
        if (userDetail == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond userDetail.errors, view:'edit'
            return
        }

        userDetail.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), userDetail.id])
                redirect userDetail
            }
            '*'{ respond userDetail, [status: OK] }
        }
    }

    @Transactional
    def delete(UserDetail userDetail) {

        if (userDetail == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        userDetail.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), userDetail.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userDetail.label', default: 'UserDetail'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
