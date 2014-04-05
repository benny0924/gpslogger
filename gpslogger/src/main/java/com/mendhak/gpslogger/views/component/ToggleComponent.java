package com.mendhak.gpslogger.views.component;

import android.view.View;

/**
 * Toggles two views. Handles the hiding and showing of two views.
 * Notifies interested handlers of the current state.
 * Created by oceanebelle on 04/04/14.
 */
public class ToggleComponent {
    private View on;
    private View off;
    private ToggleHandler handler;
    private boolean enabled;

    ToggleComponent() {}

    public static ToggleBuilder getBuilder () {
        return new ToggleBuilder(new ToggleComponent());
    }

    public static class ToggleBuilder {
        private final ToggleComponent toggleComponent;
        private boolean built;

        public ToggleBuilder(ToggleComponent toggleComponent) {
            this.toggleComponent = toggleComponent;
        }

        public ToggleBuilder addOnView(View onView) {
            ensureNotBuilt();
            if (this.toggleComponent.on != null) throw new IllegalStateException("On View already set");
            this.toggleComponent.on = onView;
            return this;
        }

        public ToggleBuilder addOffView(View onView) {
            ensureNotBuilt();
            if (this.toggleComponent.off != null) throw new IllegalStateException("Off View already set");
            this.toggleComponent.off = onView;
            return this;
        }

        public ToggleBuilder addHandler(ToggleHandler handler) {
            ensureNotBuilt();
            if (this.toggleComponent.handler != null) throw new IllegalStateException("Handler already set");
            this.toggleComponent.handler = handler;
            return this;
        }

        public ToggleBuilder setDefaultState(boolean defaultState) {
            ensureNotBuilt();
            this.toggleComponent.enabled = defaultState;
            return this;
        }

        private void ensureNotBuilt() {
            if (this.built) {
                throw new IllegalStateException("Cannot set properties on built object.");
            }
        }

        public void build() {
            this.toggleComponent.initialiseView(this.toggleComponent.on, this.toggleComponent.off);
            this.built = true;
        }
    }

    public interface ToggleHandler {
        void onStatusChange(boolean status);
    }

    private void initialiseView(View viewOn, View viewOff) {

        if (this.enabled) {
            on.setVisibility(View.VISIBLE);
            off.setVisibility(View.GONE);
        } else {
            on.setVisibility(View.GONE);
            off.setVisibility(View.VISIBLE);
        }

        viewOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enabled = true;
                on.setVisibility(View.GONE);
                off.setVisibility(View.VISIBLE);
                handler.onStatusChange(enabled);
            }
        });

        viewOff.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                enabled = false;
                off.setVisibility(View.GONE);
                on.setVisibility(View.VISIBLE);
                handler.onStatusChange(enabled);
            }
        });
    }
}