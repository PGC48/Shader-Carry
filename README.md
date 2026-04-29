# Shader Carry (Carry On Shader Fix)

A client-side add-on for the popular **[Carry On](https://modrinth.com/mod/carry-on)** mod that resolves visual rendering bugs when playing with shaders (such as Iris, Oculus, or Optifine).

### Features

* **Rendering Rewrite**: Completely rewrites the carrying renderer to utilize vanilla systems (`RenderLayer` for entities and `ItemRenderer` for blocks), replacing the custom buffers used by the original mod.
* **Improved Attachment**: Entities and blocks are properly attached to the player's body layer, ensuring they move smoothly with the camera.
* **Shader Accuracy**: Carried objects now receive 100% accurate shadows, lighting, and normal maps from active shader packs.

### Bug Fixes
This mod specifically targets two major visual issues:
1. **Invisible Entities**: Prevents animals or mobs from disappearing when picked up.
2. **Broken Lighting**: Fixes the "half-black" shadows and lighting glitches on carried blocks or chests.

### Requirements & Compatibility

* **Requires** the [Carry On](https://modrinth.com/mod/carry-on) mod to be installed.
* Fully compatible with **Iris**, **Oculus**, and standard shader packs.
* Currently available for **Minecraft 1.20.1** on the **Fabric** loader.
* **Client-side only**: Does not need to be installed on servers.

### Credits
This mod is a visual patch for **Carry On**. All credit for the original carrying mechanics goes to [Tschipp](https://modrinth.com/user/Tschipp), the creator of Carry On.

### License
Shader Carry is licensed under MIT.