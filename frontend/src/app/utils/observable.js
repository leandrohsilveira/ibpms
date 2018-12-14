import riot from 'riot';

function Observable(initialValue) {

    riot.observable(this);

    this.value = initialValue;

    this.next = (value) => {
        this.value = value;
        this.trigger('next', this.value);
    };

    this.merge = (value) => {
        if(typeof value === "function") {
            this.next({...value(this.value)});
        } else if(typeof this.value === 'object' && typeof value === 'object') {
            this.next({...this.value, ...value});
        } else {
            this.next(value);
        }
    }

    this.subscribe = (instance, fn) => {

        instance.on('mount', () => {
            this.on('next', onNext);
            fn(this.value)
        });

        instance.on('unmount', () => {
            this.off('next', onNext);
        });

        function onNext(value) {
            console.log('Next observable called', this, value);
            fn(value);
        }

    }

}

export default Observable;