package org.fxmisc.richtext;

import java.util.Optional;

public abstract class TextChange<S extends CharSequence, Self extends TextChange<S, Self>> {

    protected final int position;
    protected final S removed;
    protected final S inserted;

    public TextChange(int position, S removed, S inserted) {
        this.position = position;
        this.removed = removed;
        this.inserted = inserted;
    }

    public int getPosition() { return position; };
    public S getRemoved() { return removed; }
    public S getInserted() { return inserted; }

    protected abstract S concat(S a, S b);
    protected abstract S sub(S s, int from, int to);
    protected abstract Self create(int position, S removed, S inserted);

    /**
     * Merges this change with the given change, if possible.
     * This change is considered to be the former and the given
     * change is considered to be the latter.
     * Changes can be merged if either
     * <ul>
     *   <li>the latter's start matches the former's added text end; or</li>
     *   <li>the latter's removed text end matches the former's added text end.</li>
     * </ul>
     * @param latter change to merge with this change.
     * @return a new merged change if changes can be merged,
     * {@code null} otherwise.
     */
    public Optional<Self> mergeWith(Self latter) {
        if(latter.position == this.position + this.inserted.length()) {
            S removedText = concat(this.removed, latter.removed);
            S addedText = concat(this.inserted, latter.inserted);
            return Optional.of(create(this.position, removedText, addedText));
        }
        else if(latter.position + latter.removed.length() == this.position + this.inserted.length()) {
            if(this.position <= latter.position) {
                S addedText = concat(sub(this.inserted, 0, latter.position - this.position), latter.inserted);
                return Optional.of(create(this.position, this.removed, addedText));
            }
            else {
                S removedText = concat(sub(latter.removed, 0, this.position - latter.position), this.removed);
                return Optional.of(create(latter.position, removedText, latter.inserted));
            }
        }
        else {
            return Optional.empty();
        }
    }
}
