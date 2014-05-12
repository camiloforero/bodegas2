define([], function() {
    App.Model._BodegaModel = Backbone.Model.extend({
        defaults: {
 
		 'name' : ''
        },
        initialize: function() {
        },
        getDisplay: function(name) {
         return this.get(name);
        }
    });

    App.Model._BodegaList = Backbone.Collection.extend({
        model: App.Model._BodegaModel,
        initialize: function() {
        }

    });
    return App.Model._BodegaModel;
});