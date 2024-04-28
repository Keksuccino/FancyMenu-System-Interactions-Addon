package de.keksuccino.fmsia.optin;

import com.mojang.blaze3d.systems.RenderSystem;
import de.keksuccino.fancymenu.util.rendering.RenderingUtils;
import de.keksuccino.fancymenu.util.rendering.text.markdown.ScrollableMarkdownRenderer;
import de.keksuccino.fancymenu.util.rendering.ui.widget.button.ExtendedButton;
import de.keksuccino.fmsia.FMSIA;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OptInScreen extends Screen {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 50;
    private static final int BORDER = 40;

    private final Screen parent;
    private final Component headline = Component.translatable("fmsia.optin.screen.text.headline").withStyle(Style.EMPTY.withBold(true));
    private ScrollableMarkdownRenderer markdownRenderer;
    private final Font font = Minecraft.getInstance().font;

    public OptInScreen(@Nullable Screen parent) {
        super(Component.translatable("fmsia.optin.screen.title"));
        this.parent = parent;
    }

    protected void init() {

        int centerX = this.width / 2;
        int scrollWidth = this.width - BORDER * 2;
        int scrollHeight = this.height - HEADER_HEIGHT - FOOTER_HEIGHT;
        if (this.markdownRenderer == null) {
            this.markdownRenderer = new ScrollableMarkdownRenderer((float)(centerX - scrollWidth / 2), (float)HEADER_HEIGHT, (float)scrollWidth, (float)scrollHeight);
        } else {
            this.markdownRenderer.rebuild((float)(centerX - scrollWidth / 2), (float)HEADER_HEIGHT, (float)scrollWidth, (float)scrollHeight);
        }
        this.markdownRenderer.getMarkdownRenderer().setText("^^^\n\n\n" + I18n.get("fmsia.optin.screen.text.body") + "\n^^^");
        this.markdownRenderer.getMarkdownRenderer().setAutoLineBreakingEnabled(true);
        this.addRenderableWidget(this.markdownRenderer);

        this.addRenderableWidget(new ExtendedButton(centerX - 5 - 150, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, Component.translatable("fmsia.optin.screen.enable").withStyle(ChatFormatting.GREEN), button -> {
            try {
                OptIn.putUser(Minecraft.getInstance().getUser().getGameProfile().getId(), true);
                this.onClose();
            } catch (Exception ex) {
                LOGGER.error(FMSIA.MSG_PREFIX + " Failed to click Enable button in Opt-In screen!", ex);
            }
        }));

        this.addRenderableWidget(new ExtendedButton(centerX + 5, this.height - (FOOTER_HEIGHT / 2) - 10, 150, 20, Component.translatable("fmsia.optin.screen.disable").withStyle(ChatFormatting.RED), button -> {
            try {
                OptIn.putUser(Minecraft.getInstance().getUser().getGameProfile().getId(), false);
                this.onClose();
            } catch (Exception ex) {
                LOGGER.error(FMSIA.MSG_PREFIX + " Failed to click Disable button in Opt-In screen!", ex);
            }
        }));

    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partial) {

        this.renderBackground(graphics);

        super.render(graphics, mouseX, mouseY, partial);

        graphics.drawCenteredString(this.font, this.headline, this.width / 2, (HEADER_HEIGHT / 2) - (this.font.lineHeight / 2), -1);

    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics) {

        super.renderBackground(graphics);

        //Render header and footer separators
        RenderSystem.enableBlend();
        RenderingUtils.resetShaderColor(graphics);
        graphics.fillGradient(0, HEADER_HEIGHT, this.width, HEADER_HEIGHT + 4, -16777216, 0);
        graphics.fillGradient(0, this.height - FOOTER_HEIGHT - 4, this.width, this.height - FOOTER_HEIGHT, 0, -16777216);
        RenderingUtils.resetShaderColor(graphics);

    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        return this.markdownRenderer.mouseScrolled(mouseX, mouseY, scrollDelta);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return this.markdownRenderer.mouseReleased(mouseX, mouseY, button);
    }

    public void onClose() {
        Minecraft.getInstance().setScreen(this.parent);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

}
