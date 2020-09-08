// Copyright 2018 Siemens Product Lifecycle Management Software Inc.

/**
 * @module js/booksAuthenticatorService
 */
import app from 'app';
import AwPromiseService from 'js/awPromiseService';

let exportsObj = {};
exportsObj.getAuthenticator = function() {
    var exports = {};

    /**
     * This function determines if authentication is required. If already authenticated, none is required.
     *
     * @return {Promise} if promise is resolved, then no authentication is required. if promise fails, then the authenticate function will be called.
     */
    exports.checkIfSessionAuthenticated = function() {
        return AwPromiseService.instance.resolve();
    };

    /**
     * authenticator specific function to carry out authentication. In the interactive case, we just resolve
     * directly to continue the pipeline.
     */
    exports.authenticate = function() {
        return AwPromiseService.instance.resolve();
    };

    /**
     * this is called during the authentication process. It gets invoked after the authentication is
     * completed/ready. It is a spot to do any session level initialization.
     *
     * @return {Promise} promise to be resolved after the authenticator does self initialization
     */
    exports.postAuthInitialization = function() {
        return AwPromiseService.instance.resolve();
    };

    /**
     * triggers the authenticator sign out logic. Returns a promise invoked upon completion
     *
     * @return {Promise} promise to be invoked upon completion of the signout tasks
     */
    exports.signOut = function() {
        return AwPromiseService.instance.resolve();
    };

    return exports;
}
export default exportsObj;
app.factory('booksAuthenticatorService', () => exportsObj);