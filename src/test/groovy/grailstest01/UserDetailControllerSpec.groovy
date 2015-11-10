package grailstest01

import grails.test.mixin.*
import spock.lang.*

@TestFor(UserDetailController)
@Mock(UserDetail)
class UserDetailControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.userDetailList
            model.userDetailCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.userDetail!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def userDetail = new UserDetail()
            userDetail.validate()
            controller.save(userDetail)

        then:"The create view is rendered again with the correct model"
            model.userDetail!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            userDetail = new UserDetail(params)

            controller.save(userDetail)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/userDetail/show/1'
            controller.flash.message != null
            UserDetail.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def userDetail = new UserDetail(params)
            controller.show(userDetail)

        then:"A model is populated containing the domain instance"
            model.userDetail == userDetail
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def userDetail = new UserDetail(params)
            controller.edit(userDetail)

        then:"A model is populated containing the domain instance"
            model.userDetail == userDetail
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/userDetail/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def userDetail = new UserDetail()
            userDetail.validate()
            controller.update(userDetail)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.userDetail == userDetail

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            userDetail = new UserDetail(params).save(flush: true)
            controller.update(userDetail)

        then:"A redirect is issued to the show action"
            userDetail != null
            response.redirectedUrl == "/userDetail/show/$userDetail.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/userDetail/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def userDetail = new UserDetail(params).save(flush: true)

        then:"It exists"
            UserDetail.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(userDetail)

        then:"The instance is deleted"
            UserDetail.count() == 0
            response.redirectedUrl == '/userDetail/index'
            flash.message != null
    }
}
