import messageObservable from './message';

<app-message>

    <div class={'message': true, 'show': !!description}>
        <div class="description">{description}</div>
        <button type="button" class="button button-clear" onclick={handleDismissClick}>Dispensar</button>
    </div>

    <style>

        :scope * {
            box-sizing: border-box;
        }

        :scope > .message {
            display: flex;
            align-items: center;
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            height: 0;
            background-color: black;
            color: #9A46C5;
            transition: height 0.5s;
        }

        :scope > .message > * {
            display: none;
        }

        :scope > .message.show {
            height: 7rem;
            padding: 0 3rem;
        }

        :scope > .message.show > * {
            display: block;
        }

        :scope > .message > button {
            margin-bottom: 0;
            padding: 0 1rem;
        }

        :scope > .message > .description {
            color: white;
            flex: 1;
        }

        @media (max-width: 40.0rem) {
            :scope > .message.show {
                height: 7rem;
                padding: 0 0.5rem;
            }

            :scope > .message > .description {
                font-size: 0.9em;
            }
        }

    </style>

    <script>

        this.description = null;
        this.timeout = null;

        this.handleDismissClick = () => {
            messageObservable.next();
        }

        messageObservable.subscribe(this, description => {
            if(this.timeout) {
                clearTimeout(this.timeout);
            }
            this.update({
                description, 
                timeout: description && setTimeout(() => this.handleDismissClick(), opts.dismissTime || 5000)
            });
        });


    </script>
</app-message>