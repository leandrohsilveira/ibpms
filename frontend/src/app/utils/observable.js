import riot from 'riot';

/**
 * A RXJS Subject like observable for RiotJS.
 * 
 * @param {any} initialValue the observable's initial value 
 */
function Observable(initialValue) {

    riot.observable(this);

    /**
     * observable's current value, avoid reading it directly. Use "subscribe" or "observe" functions instead.
     */
    this.value = initialValue;

    /**
     * Literally set this observable next value, notifying all observers of it.
     * 
     * @param {any} value the next value of this observable
     */
    this.next = (value) => {
        this.value = value;
        this.trigger('next', this.value);
    };

    /**
     * Merge complex objects as the next value of this observable. Be sure of always set a object to this observable.
     * @param {any} value an object to merge with current state or a function to manually merge it returning the next value. 
     *          If the current value and the provided value are objects, both will be merged as the next value.
     *          If the provided value is a function, it will be called with the current value as parameter, and the returned value will be set as next value.
     *          If the current value or the provided value are not an object or a function, it will simply set as this observable next value 
     */
    this.merge = (value) => {
        if(typeof value === "function") {
            this.next({...value(this.value)});
        } else if(typeof this.value === 'object' && typeof value === 'object') {
            this.next({...this.value, ...value});
        } else {
            this.next(value);
        }
    };

    /**
     * Simply subscribe to changes
     * @param {any} instance a riot observable instance.
     * @param {Function} fn the observable change listener
     */
    this.subscribe = (instance, fn) => {

        instance.on('mount', () => {
            this.on('next', onNext);
            fn(this.value)
        });

        instance.on('unmount', () => {
            this.off('next', onNext);
        });

        function onNext(value) {
            fn(value);
        }

    };

    /**
     * Subscribe to changes an map the changed value to a riot observable state.
     * @param {any} instance a riot observable instance.
     * @param {Function} mapper the mapper funtion to map value changes to observable instance state.
     */
    this.observe = (instance, mapper) => {
        this.subscribe(instance, value => {
            this.update(mapper(value));
        });
    };

}

export default Observable;