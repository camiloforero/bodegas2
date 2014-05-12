define([], function() {
    App.Model._BodegaMasterModel = Backbone.Model.extend({
     
    });

    App.Model._BodegaMasterList = Backbone.Collection.extend({
        model: App.Model._BodegaMasterModel,
        initialize: function() {
        }

    });
    return App.Model._BodegaMasterModel;
    
});