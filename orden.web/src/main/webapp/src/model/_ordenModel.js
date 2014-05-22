define([], function() {
    App.Model._OrdenModel = Backbone.Model.extend({
        defaults: {
 
		 'name' : ''
 ,  
		 'tipo' : ''
 ,  
		 'estado' : ''
 ,  
		 'cantidad' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
         return this.get(name);
        }
    });

    App.Model._OrdenList = Backbone.Collection.extend({
        model: App.Model._OrdenModel,
        initialize: function() {
        }

    });
    return App.Model._OrdenModel;
});